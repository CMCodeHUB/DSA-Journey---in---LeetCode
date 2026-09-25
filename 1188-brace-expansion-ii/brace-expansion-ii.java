class Solution {
    private String s;
    private int pos;

    public List<String> braceExpansionII(String expression) {
        s = expression;
        pos = 0;

        Set<String> set = parseExpression();
        List<String> result = new ArrayList<>(set);

        Collections.sort(result);
        return result;
    }

    private Set<String> parseExpression() {
        Set<String> result = parseTerm();

        while (pos < s.length() && s.charAt(pos) == ',') {
            pos++;
            result.addAll(parseTerm());
        }

        return result;
    }

    private Set<String> parseTerm() {
        Set<String> result = new HashSet<>();
        result.add("");

        while (pos < s.length()
                && s.charAt(pos) != '}'
                && s.charAt(pos) != ',') {

            Set<String> next;

            if (s.charAt(pos) == '{') {
                pos++;
                next = parseExpression();
                pos++; // skip '}'
            } else {
                next = new HashSet<>();
                next.add(String.valueOf(s.charAt(pos)));
                pos++;
            }

            Set<String> temp = new HashSet<>();

            for (String a : result) {
                for (String b : next) {
                    temp.add(a + b);
                }
            }

            result = temp;
        }

        return result;
    }
}