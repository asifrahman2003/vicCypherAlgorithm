/*
 * Author: Muhammad Asifur Rahman
 * Date: 09/21/2024
 * Course Name: CSc 345
 * Programming assignment: 01
 * Program Purpose: This program implements the decryption operations for the VIC (VIC InComplete) cipher. 
 *   				The program reads encrypted data, processes it through various VIC operations, and 
 *   				outputs the decrypted message based on the provided cipher methods and data structures.
 */

import java.util.ArrayList;


public class DecryptVIC {
    public static void main(String[] arguments) {
		EncryptVIC.VICData readData = EncryptVIC.readVICData(arguments[0], false);

		String str1 = VICOperations.noCarryAddition(readData.agentID, readData.date.substring(0, 5));
		String str2 = VICOperations.chainAddition(str1, 10);
		String str3 = VICOperations.digitPermutation(readData.phrase);
		String str4 = VICOperations.noCarryAddition(str2, str3);
		String str5 = VICOperations.digitPermutation(str4);
		ArrayList<String> boardKey = VICOperations.straddlingCheckerboard(str5, readData.anagram);
		char[] numSpace = VICOperations.numberSpaces(str5, readData.anagram);
		int lastDigit = Character.getNumericValue(readData.date.charAt(5));

		if (readData.message.length() - lastDigit < 5) {
			lastDigit = readData.message.length() - 5;
		}
		String tempString = readData.message.substring(0, lastDigit);
		tempString = tempString + readData.message.substring(lastDigit + 5, readData.message.length());
		String currChar;
		for (int index = 0; index < tempString.length(); ) {
            if (index < tempString.length() && 
                (tempString.charAt(index) == numSpace[0] || tempString.charAt(index) == numSpace[1])) {
                currChar = tempString.substring(index, index + 2);
                index += 2; // Skip the next character as it's part of the space
            } else { 
                currChar = "" + tempString.charAt(index); 
                index++; // Move to the next character
            }
            System.out.print((char) (boardKey.indexOf(currChar) + 65)); 
        }
    }
}

