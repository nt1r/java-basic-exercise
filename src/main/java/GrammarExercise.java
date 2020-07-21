import java.util.*;

public class GrammarExercise {
    public static void main(String[] args) {
        //需要从命令行读入
        String firstWordList = "";
        String secondWordList = "";

        List<String> result = findCommonWordsWithSpace(firstWordList, secondWordList);
        //按要求输出到命令行
    }

    public static List<String> findCommonWordsWithSpace(String firstWordList, String secondWordList) {
        //在这编写实现代码

        // check input validity
        if (!checkInvalidInput(firstWordList) || !checkInvalidInput(secondWordList)) {
            throw new RuntimeException("input not valid");
        }

        // split words by "," pattern
        List<String> firstSplitList = splitByPattern(firstWordList, ",");
        List<String> secondSplitList = splitByPattern(secondWordList, ",");

        // core function here, match common words of two string lists
        List<String> commonWordsList = matchCommonWords(firstSplitList, secondSplitList);

        // remove repeated words
        removeRepeatedWords(commonWordsList);

        // add space characters before output
        addSpaceInEachWord(commonWordsList);

        return commonWordsList;
    }

    /* check input validity */

    /***
     * Check whether input string {@code inputStr} is valid or not.
     * @param inputStr one input string
     * @return {@code true}: valid | {@code false}: invalid
     */
    private static boolean checkInvalidInput(String inputStr) {
        return checkNonLetter(inputStr) && checkNonTwoContinuousCommas(inputStr);
    }

    /***
     * Check whether there are non-letter characters in the input string {@code inputStr}. (except comma and space)
     * @param inputStr one input string
     * @return {@code true}: All characters in {@code inputStr} are letter characters.
     *      | {@code false}: There are non-letter characters in {@code inputStr}.
     */
    private static boolean checkNonLetter(String inputStr) {
        for (int i = 0; i < inputStr.length(); ++i) {
            char currentChar = inputStr.charAt(i);
            if (!Character.isLetter(currentChar) && currentChar != ',' && currentChar != ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether there are two continuous commas in the input string {@code inputStr}.
     *
     * @param inputStr one input string
     * @return {@code true}: There are NOT two continuous commas.
     * | {@code false}: There are two continuous commas.
     */
    private static boolean checkNonTwoContinuousCommas(String inputStr) {
        return !inputStr.contains(",,");
    }
    /* check input validity */


    /* split words */

    /**
     * Split the whole input string into each word by target pattern {@code pattern}.
     *
     * @param inputStr one input string
     * @param pattern  the target string pattern for splitting {@code inputStr}
     * @return the split result in the form of {@code List}
     */
    private static List<String> splitByPattern(String inputStr, String pattern) {
        String[] splitStrArray = inputStr.split(pattern);
        List<String> splitStrList = Arrays.asList(splitStrArray);
        // to upper case
        List<String> splitStrList_uppercase = new ArrayList<>();

        ListIterator<String> iterator = splitStrList.listIterator();
        while (iterator.hasNext()) {
            splitStrList_uppercase.add(iterator.next().toUpperCase());
        }
        return splitStrList_uppercase;
    }
    /* split words */


    /**
     * Match all common words of two string lists.
     *
     * @param firstStrList  the list {@code List} of first split strings
     * @param secondStrList the list {@code List} of second split strings
     * @return a list {@code List} which contains all common words in {@code firstStrList} and {@code secondStrList}
     */
    private static List<String> matchCommonWords(List<String> firstStrList, List<String> secondStrList) {
        // sort first for comparison
        sortStrListByAscendingOrder(firstStrList);
        sortStrListByAscendingOrder(secondStrList);

        // store common words
        List<String> commonWordList = new ArrayList<>();

        ListIterator<String> iterator_first = firstStrList.listIterator();
        ListIterator<String> iterator_second = secondStrList.listIterator();

        // init tmp string of words in two lists
        String strFirst = iterator_first.hasNext() ? iterator_first.next() : "";
        String strSecond = iterator_second.hasNext() ? iterator_second.next() : "";

        // comparison between two sorted string list
        while (iterator_first.hasNext() && iterator_second.hasNext()) {
            if (strFirst.contentEquals(strSecond)) {
                commonWordList.add(strFirst);
                strFirst = iterator_first.next();
                strSecond = iterator_second.next();
            } else if (strFirst.compareTo(strSecond) < 0) {
                strFirst = iterator_first.next();
            } else {
                strSecond = iterator_second.next();
            }
        }

        return commonWordList;
    }

    /**
     * Sort string list by ascending order.
     *
     * @param strList the source list {@code List} which has not been sorted
     */
    private static void sortStrListByAscendingOrder(List<String> strList) {
        strList.sort(new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });
    }

    /**
     * Remove repeated words in string list which has been sorted.
     *
     * @param strList the raw sorted list {@code List} which may contain repeated words
     */
    private static void removeRepeatedWords(List<String> strList) {
        if (!strList.isEmpty()) {
            ListIterator<String> iterator = strList.listIterator();
            String lastStr = null;
            while (iterator.hasNext()) {
                String currentStr = iterator.next();
                if (lastStr != null && currentStr.equals(lastStr)) {
                    iterator.remove();
                } else {
                    lastStr = currentStr;
                }
            }
        }
    }

    /* add space character */


    /**
     * Add space characters in each word of string list.
     * Space characters should be added between each character of word.
     *
     * @param strList the source list {@code List} which space character has not been added into each word in it
     */
    private static void addSpaceInEachWord(List<String> strList) {
        ListIterator<String> iterator = strList.listIterator();
        while (iterator.hasNext()) {
            String currentStr = iterator.next();
            String spaceInsertedStr = addSpaceInStr(currentStr);

            // remove raw string and add new string
            iterator.remove();
            iterator.add(spaceInsertedStr);
        }
    }

    /**
     * Add space characters in target string. e.g. 'love' -> 'l o v e'
     *
     * @param str the target string
     * @return a string which space characters has been added between each character
     */
    private static String addSpaceInStr(String str) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char c : str.toCharArray()) {
            stringBuilder.append(c);
            stringBuilder.append(' ');
        }

        String insertedStr = stringBuilder.toString();
        // remove last space character
        return insertedStr.substring(0, insertedStr.length() - 1);
    }
    /* add space character */
}
