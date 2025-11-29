# Java Project – Cryptography Algorithms

This repository contains the implementation of several **cryptographic algorithms** in Java.  
The goal of the project is to demonstrate encryption, decryption, and primality testing techniques using standard algorithms and classical ciphers.

---

## Repository Structure

```
crypto_project/
│
├── implementation/
│ ├── AES_CTR.java
│ ├── DES_CBC.java
│ ├── Main.java
│ ├── MillerRabin.java
│ ├── RSA.java
│ └── Trasposizione.java
├── LICENSE
└── README.md
```

---

## Project Objective

The project demonstrates:

- **AES** encryption in CTR mode  
- **DES** encryption in CBC mode  
- **RSA** public-key encryption and decryption  
- **Miller-Rabin** primality testing  
- **Classical transposition cipher**  
- A **Main** class to select and run any of the ciphers  

---

### How to run the application

1. Compile all Java files:
```bash
javac *.java
```

2. Run the main class:
```bash
java Main
```

3. Follow the on-screen prompts to choose the algorithm and provide input data.

---

## Classes Overview

- **AES_CTR** – AES cipher implementation in CTR mode
- **DES_CBC** – DES cipher implementation in CBC mode
- **RSA** – RSA encryption and decryption
- **MillerRabin** – Primality test
- **Trasposizione** – Classical transposition cipher
- **Main** – Entry point to select and execute one of the algorithms

---

## Notes

- Input can be text messages or numeric keys depending on the algorithm
- Ensure Java 8 or higher is installed to compile and run the project
- The project demonstrates both modern and classical encryption techniques, along with basic cryptographic testing
