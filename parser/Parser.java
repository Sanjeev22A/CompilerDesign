import java.util.*;

public class Parser {
    String input;
    int currentState = -1;
    int operationIndex;
    static HashSet<String> keywords = new HashSet<String>() {
        {
            add("auto");
            add("break");
            add("case");
            add("char");
            add("const");
            add("continue");
            add("default");
            add("do");
            add("double");
            add("else");
            add("enum");
            add("extern");
            add("float");
            add("for");
            add("goto");
            add("if");
            add("int");
            add("long");
            add("register");
            add("return");
            add("short");
            add("signed");
            add("sizeof");
            add("static");
            add("struct");
            add("switch");
            add("typedef");
            add("union");
            add("unsigned");
            add("void");
            add("volatile");
            add("while");
            add("_Alignas");
            add("_Alignof");
            add("_Atomic");
            add("_Bool");
            add("_Complex");
            add("_Generic");
            add("_Imaginary");
            add("_Noreturn");
            add("_Static_assert");
            add("_Thread_local");
            add("inline");
            add("restrict");
            add("__asm__");
            add("__volatile__");
        }
    };

    Parser(String input) {
        this.input = input;
    }

    boolean isAlpha(char c) {
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_') {
            return true;
        }
        return false;
    }

    boolean isDigit(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        return false;
    }

    boolean isOperator(char c) {
        if (c == '+' || c == '-' || c == '/' || c == '*' || c == '=') {
            return true;
        }
        return false;
    }

    boolean isTrio(char c) {
        if (c == '+' || c == '-' || c == '=') {
            return true;
        }
        return false;
    }

    boolean isIdentifier(int start, int end) {

        if (start <= end) {
            int i = start;
            if (!isAlpha(input.charAt(i))) {
                return false;
            }
            i++;
            while (i <= end) {
                if (!(isAlpha(input.charAt(i)) || isDigit(input.charAt(i)))) {
                    return false;
                }
                i++;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean isKeyword(int start, int end) {
        String s = "";
        for (int i = start; i <= end; i++) {
            s += input.charAt(i);
        }
        return keywords.contains(s);
    }

    public boolean isConstant(int start, int end) {
        if (start > end) {
            return false;
        }
        for (int i = start; i <= end; i++) {
            if (!isDigit(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean isOperator(int start, int end) {
        if (start == end) {
            return isOperator(input.charAt(start));
        } else {
            if (start + 1 != end) {
                return false;
            } else if (start + 1 == end) {
                if (input.charAt(start) == input.charAt(end) && isTrio(input.charAt(start))) {
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
    }

    int returnState(int start, int end) {
        if (input.charAt(end) == ' ') {
            return 6;
        } else if (isKeyword(start, end)) {
            return 1;
        } else if (isConstant(start, end)) {
            return 2;
        } else if (isIdentifier(start, end)) {
            return 3;
        } else if (isOperator(start, end)) {
            return 4;
        } else {
            return 5;
        }
    }

    boolean stateDefn(int start, int end) {
        // System.out.println(currentState);
        int aState = returnState(start, end);
        return aState == currentState;
    }

    void parseFunc(String input) {
        int start = 0, end = 0;
        while (end < input.length()) {
            // System.out.println(stateDefn(start, end));
            if (currentState == -1) {
                currentState = returnState(start, end);
                end++;
                continue;

            } else if (!stateDefn(start, end) && returnState(start, end) == 1) {
                String s = input.substring(start, end+1);
                System.out.println(s + " is of type " + returnState(start, end));
                end++;
                start = end;
                currentState = -1;
            } else if (!stateDefn(start, end)) {
                String s = input.substring(start, end);
                System.out.println(s + " is of type " + currentState);
                start = end;
                currentState = -1; // Reset the state after printing
            } else {
                currentState = returnState(start, end);
                end++;
            }
        }
        // Handle the last token
        if (start < end) {
            String s = input.substring(start, end);
            System.out.println(s + " is of type " + currentState);
        }
    }
}
