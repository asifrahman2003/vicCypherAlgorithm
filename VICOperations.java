/*
 * Author: Muhammad Asifur Rahman
 * Date: 09/21/2024
 * Course Name: CSc 345
 * Programming assignment: 01
 * Program Purpose: The program implements various operations for the VIC (VIC InComplete) cipher, 
 *                  a classical encryption technique that combines a number of cryptographic 
 *                  methods to enhance security. Here, the VIC cipher utilizes a series of 
 *                  transformations, including arithmetic operations and character rearrangements, 
 *                  to encrypt messages effectively. 
 *                  
 *                  The primary functions implemented in this program as required are:
 *                  1. No-Carry Addition: This operation adds two numeric strings without carrying 
 *                     over, meaning that if the sum of two digits exceeds 9, only the last digit of 
 *                     the result is retained. This technique is essential for the VIC cipher, 
 *                     where addition is used to generate sequences based on initial values.
 *                  
 *                  2. Chain Addition: This method extends a numeric string to a specified length 
 *                     by performing sequential no-carry additions of its digits, creating a longer 
 *                     numeric string necessary for the cipher's operations. 
 *                  
 *                  3. Digit Permutation: This function generates a string of numeric values 
 *                     representing the frequency and order of unique characters within a given input. 
 *                     This is crucial for establishing a mapping that the cipher relies on during 
 *                     encryption and decryption processes. 
 *                  
 *                  4. Straddling Checkerboard: The checkerboard is a character encoding grid 
 *                     used to map letters to numeric values based on a permutation of characters and 
 *                     an anagram. This encoding scheme adds a layer of complexity to the encryption 
 *                     process, allowing for more secure representation of data.
 *                  
 *                  By implementing these operations, the program demonstrates how the VIC cipher 
 *                  effectively encrypts and decrypts messages, showcasing the importance of 
 *                  mathematical transformations and character manipulations.
 */


import java.util.ArrayList; 
import java.util.Collections; 


public class VICOperations { 
    /**
     * Performs the required no-carry addition on two numeric strings.
     * 
     * @param num1String Takes first input number as a string.
     * @param num2String Takes second input number as a string.
     * @return Returns the result of no-carry addition as a string. 
     */
    public static String noCarryAddition (String num1String, String num2String) {
        // Checks if the input strings are of same length or not and selects the maximum and
        // minimum lengths of the strings. 
        StringBuilder finalResult = new StringBuilder();
        int strLength = num1String.length();
        int i = 0;

        // Use while loop to iterate through the digits
        while (i < strLength) {
            int digit1 = Character.getNumericValue(num1String.charAt(i));
            int digit2 = Character.getNumericValue(num2String.charAt(i));
            int sumDigits = (digit1 + digit2) % 10;
            finalResult.append(sumDigits);
            i++; // Increment index
        }
        
        return finalResult.toString();
    }

    /**
     * Performs the extension of a numeric string to a specified size using chain addition.
     * 
     * @param numberString Initial number string.
     * @param length Desired length of the result string.
     * @return Returns the extended number string.
     */
    public static String chainAddition (String numberString, int length) {
        StringBuilder finalResult = new StringBuilder();  // Using StringBuilder for efficiency
    
        // Copy the original input to the result up to the input's length or desired size
        int i = 0; // Initialize index for while loop
        while (i < Math.min(numberString.length(), length)) {
            finalResult.append(numberString.charAt(i));
            i++; // Increment index
        }
        
        // Perform chain addition to extend the number to the desired size
        int j = 0;  // Index to track the current position in the result string
        while (finalResult.length() < length) {
            // Get the last two digits to perform no-carry addition
            String secLastDigit = String.valueOf(finalResult.charAt(j));      // Digit at position i
            String lastDigit = String.valueOf(finalResult.charAt(j + 1));        // Digit at position i+1
            
            // Perform no-carry addition on these two digits
            String sumDigits = noCarryAddition(secLastDigit, lastDigit);
            
            // Append the last digit of the sum to the result
            finalResult.append(sumDigits.charAt(sumDigits.length() - 1));
            
            j++;  // Move to the next pair of digits
        }
    
        // Return the resulting number as a string
        return finalResult.toString();
    }

    /**
     * Performs the generation of a digit permutation based on the frequency of 
     * characters in the first 10 characters of the input string.
     * 
     * @param string Takes the input string.
     * @return Returns the string representing the digit permutation.
     */
    public static String digitPermutation(String string) {
    // Returns null if the input string has fewer than 10 characters
    if (string.length() < 10) {
        return null;
    }

    // Initialize data structures to track unique letters and their frequencies
    StringBuilder permutationResults = new StringBuilder();
    ArrayList<Character> uniqueLetterLists = new ArrayList<>();
    ArrayList<Integer> frequency = new ArrayList<>();
    ArrayList<Integer> sortedFrequencies = new ArrayList<>();
    ArrayList<Character> sortedLetters = new ArrayList<>();

    // Process the first 10 characters of the input string using while loop
    int index = 0;
    while (index < 10) {
        char currCharacter = string.charAt(index);
        // Update frequency of existing letters, or add new ones
        if (uniqueLetterLists.contains(currCharacter)) {
            int currIndex = uniqueLetterLists.indexOf(currCharacter);
            frequency.set(currIndex, frequency.get(currIndex) + 1);
        } else {
            uniqueLetterLists.add(currCharacter);
            frequency.add(1);
        }
        index++;
    }

    // Create a sorted version of the letters and frequencies using for loop
    for (int j = 0; j < uniqueLetterLists.size(); j++) {
        sortedLetters.add(uniqueLetterLists.get(j));
    }
    Collections.sort(sortedLetters);

    // Copy frequencies based on the sorted letters using while loop
    index = 0;
    while (index < uniqueLetterLists.size()) {
        sortedFrequencies.add(frequency.get(uniqueLetterLists.indexOf(sortedLetters.get(index))));
        index++;
    }

    // Creates the digit permutation by determining the position of each character using while loop
    index = 0;
    while (index < 10) {  
        int permutationValue = 0; 
        char currentInputCharacter = string.charAt(index);
        int sortedIndex = sortedLetters.indexOf(currentInputCharacter);

        // Calculate the permutation value by adding frequencies of earlier letters using for loop
        for (int j = 0; j < sortedIndex; j++) {
            permutationValue += sortedFrequencies.get(j);
        }

        // Include the frequency of the current letter minus how many times it has been used
        permutationValue += sortedFrequencies.get(sortedIndex) - frequency.get(uniqueLetterLists.indexOf(currentInputCharacter));

        // Append the calculated permutation value to the result
        permutationResults.append(permutationValue);

        // Reduce the frequency of the current letter
        frequency.set(uniqueLetterLists.indexOf(currentInputCharacter), frequency.get(uniqueLetterLists.indexOf(currentInputCharacter)) - 1);

        index++;
    }

    return permutationResults.toString();
}

/**
 * This class generates a straddling checkerboard based on the given permutation and anagram. 
 * 
 * This straddling checkerboard is a character encoding scheme that uses a grid to represent 
 * letters alongside numeric values. This method checks the validity of the inputs, 
 * counts spaces in the anagram, and constructs the checkerboard if the inputs meet the 
 * necessary criteria. 
 *
 * @param permutation    Takes a string of 10 unique characters used for the first row of the checkerboard.
 *                       This string must not contain any numeric characters.
 * @param anagram        Takes a string of 10 characters where two characters are spaces and the others
 *                       must be unique letters that are not present in the permutation.
 * @return               Returns an ArrayList of strings representing letter encodings if the inputs 
 *                       are valid; otherwise, returns null.
 *         
 * The method performs the following steps:
 * 1. Validates the lengths of the inputs to ensure they are both exactly 10 characters long.
 * 2. Checks that the permutation contains no numeric characters and that the anagram 
 *    contains exactly two spaces.
 * 3. Constructs a list of letters that are not included in the anagram.
 * 4. Initializes a 2D character array to serve as the checkerboard.
 * 5. Fills the checkerboard with the permutation, anagram, and remaining letters.
 * 6. Lastly, generates the letter encoding based on the positions of characters in the checkerboard.
 */
public static ArrayList<String> straddlingCheckerboard(String permutation, String anagram) {
    // Check if inputs are valid
    if (permutation.length() != 10 || anagram.length() != 10) { 
        return null; 
    } 

    String inputNums = "1234567890"; 
    int count = 0; 
    int index = 0;

    // Loop through the perm string using while loop
    while (index < permutation.length()) {
        if (!inputNums.contains(Character.toString(permutation.charAt(index))) ||
            inputNums.contains(Character.toString(anagram.charAt(index)))) {
            return null;
        }
        if (anagram.charAt(index) == ' ') {
            count++;
        }
        index++;
    } 

    if (count != 2) {
        return null;
    }

    // Initialize variables
    ArrayList<String> finalResult = new ArrayList<>();          // The list of letter encodings that will be returned
    ArrayList<Character> letter = new ArrayList<>();            // The list of letters that are not in the anagram
    char[][] characterBoard = new char[4][11];                  // The board for picking letter encodings
    char[] spaces = numberSpaces(permutation, anagram);               // The nums associated with each space
    String currString = "";                                     // The string that will be added as an element to result

    // Create a list of all letters not in the anagram using a while loop
    char j = 'A'; 
    while (j <= 'Z') { 
        letter.add(j); 
        j++; // Increment character 
    } 

    index = 0; 
    while (index < 10) {
        if (index != spaces[0] && index != spaces[1]) {
            letter.remove(Character.valueOf(anagram.charAt(index)));
        }
        index++;
    }

    // Set first column
    characterBoard[0][0] = '$';
    characterBoard[1][0] = '$';
    characterBoard[2][0] = spaces[0];
    characterBoard[3][0] = spaces[1];

    // Fill board using a while loop
    index = 1; // Initialize index for filling the board
    while (index < 11) {
        characterBoard[0][index] = permutation.charAt(index - 1);
        characterBoard[1][index] = anagram.charAt(index - 1);
        characterBoard[2][index] = letter.get(index - 1);
        if (index < 9) {
            characterBoard[3][index] = letter.get(index + 9);
        } else {
            characterBoard[3][index] = '$';
        }
        index++;
    }

    // Iterate through the board using a while loop
    char currChar = 'A';
    while (currChar <= 'Z') {
        index = 1;
        while (index < 4) {
            int jIndex = 1;
            while (jIndex < 11) {
                if (characterBoard[index][jIndex] == currChar) {
                    currString = "" + characterBoard[index][0] + characterBoard[0][jIndex];
                    if (currString.charAt(0) == '$') {
                        currString = "" + currString.charAt(1);
                    }
                    finalResult.add(currString);
                }
                jIndex++;
            }
            index++;
        }
        currChar++; // Increment character
    }
    return finalResult;
}

public static char[] numberSpaces(String perm, String anagram) {
    // Initialize result
    char[] numSpace = { '$', '$' };

    // Iterate through the anagram using a while loop
    int i = 0;
    while (i < 10) {
        if (anagram.charAt(i) == ' ') { 
            if (numSpace[0] == '$') { 
                numSpace[0] = perm.charAt(i); 
            } else {
                numSpace[1] = perm.charAt(i); 
            }
        } 
        i++; 
    } 
    return numSpace; 
}

    public static void main(String[] args) {
        System.out.println(noCarryAddition("73003", "45050"));
        System.out.println(chainAddition("18053", 10));
        System.out.println(digitPermutation("DONTDODRUG"));
        System.out.println(straddlingCheckerboard("4071826395", "a tin shoe")); 
    } 
} 

