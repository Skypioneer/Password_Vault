package encrypt;

import cwang_hw2.CaesarCipher;      //Needed for CaesarCipher object

/**
 * The CaesarCipherTest class test if a password is same before and after
 * encryptor and return the result.
 */
public class CaesarCipherTest {
    String originalCode, encryptedCode, decryptedCode;

    /**
    * The test function encrypts and decrypts an input password and then store
    * them for the need to return.
    *
    * @param originalCode the user's input code
    */
    public void test(String originalCode)
    {
        CaesarCipher caesarCipher = new CaesarCipher();

        this.originalCode = originalCode;

        encryptedCode = caesarCipher.encrypt(originalCode);

        decryptedCode = caesarCipher.decrypt(encryptedCode);
    }

    /**
    * The getEncryptedCode function return the encrypted code.
    *
    * @return The encrypted code.
    */
    public String getEncryptedCode(){
        return encryptedCode;
    }

    /**
     * The getEncryptedCode function return the decrypted code.
     *
     * @return The decrypted code.
     */
    public String getDecryptedCode(){
        return decryptedCode;
    }

    /**
    * The isSame function return the result if a password is same before and
    * after encryptor.
    */
    public boolean isSame(){
        return originalCode.equals(decryptedCode);
    }
}
