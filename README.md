# VIC Cipher – Java Implementation

This is a simplified implementation of the VIC cipher, a historical encryption method, completed at the University of Arizona. It includes both encryption and decryption tools using a reduced version of the original VIC algorithm. 

## Project Structure
├── EncryptVIC.java           # Main encryption program
├── DecryptVIC.java           # Main decryption program
├── VICOperations.java        # Shared utility methods
├── readVICdata.txt           # Sample input file for encryption
├── decrypt.txt               # Sample input file for decryption
├── EncryptVIC.jar            # Compiled encryption JAR
├── DecryptVIC.jar            # Compiled decryption JAR
├── manifest_encrypt.txt      # Manifest for encryption
├── manifest_decrypt.txt      # Manifest for decryption
└── prog01.pdf                # Project description/specification

---

## Algorithm Overview

The VIC cipher here uses:
- **No-carry addition**
- **Chain addition**
- **Digit permutation**
- **Straddling checkerboard**

It transforms a message into an encrypted sequence of digits and embeds the agent ID using a reversible method. The decryption process reverses this to recover the original message.

## 📥 Sample Input Files
(for encryption)

85721
470918
Choose the representation that best supports the operations
heat is on
Run away!

(for decryption) 
470918
Choose the representation that best supports the operations
heat is on
3532423085721239

## Author

Asifur Rahman
Computer Science, 
University of Arizona
📧 asifrahman@arizona.edu
🌐 asifrahman.online

## License

This project is built for educational purposes only.
Do not use it to encrypt real secrets, it’s not NSA-proof! 😉
