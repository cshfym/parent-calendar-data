package com.parentcalendar.services.security

import grails.transaction.Transactional
import org.springframework.stereotype.Service

import javax.crypto.Cipher
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.RSAPrivateKeySpec
import java.security.spec.RSAPublicKeySpec

@Transactional
@Service
class ParentCalendarSecurityService {

  public void rsaTest() {
      byte[] data = "Hello from RSA!".bytes
      byte[] encrypted = rsaEncryptWithPublicKey(data)
      byte[] decrypted = rsaDecryptWithPrivateKey(encrypted)
      String d = new String(decrypted)
      println d
  }
  public byte[] rsaEncryptWithPublicKey(byte[] data) {
    PublicKey pubKey = readPublicKeyFromFile("data/public.key")
    Cipher cipher = Cipher.getInstance("RSA")
    cipher.init(Cipher.ENCRYPT_MODE, pubKey)
    cipher.doFinal(data)
  }

  public byte[] rsaDecryptWithPrivateKey(byte[] data) {
    PrivateKey privKey = readPrivateKeyFromFile("data/private.key")
    Cipher cipher = Cipher.getInstance("RSA")
    cipher.init(Cipher.DECRYPT_MODE, privKey)
    cipher.doFinal(data)
  }

  private PublicKey readPublicKeyFromFile(String keyFileName) throws IOException {
    InputStream inputStream = this.class.classLoader.getResourceAsStream(keyFileName)
    ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(inputStream))
    def publicKey
    try {
      BigInteger m = (BigInteger) oin.readObject()
      BigInteger e = (BigInteger) oin.readObject()
      RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e)
      KeyFactory fact = KeyFactory.getInstance("RSA")
      publicKey = fact.generatePublic(keySpec)
    } catch (Exception e) {
      throw new RuntimeException("RSA Key Serialization Error", e)
    } finally {
      oin.close()
    }
    publicKey
  }

  private PrivateKey readPrivateKeyFromFile(String keyFileName) throws IOException {
    InputStream inputStream = this.class.classLoader.getResourceAsStream(keyFileName)
    ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(inputStream))
    def privateKey
    try {
      BigInteger m = (BigInteger) oin.readObject()
      BigInteger e = (BigInteger) oin.readObject()
      RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e)
      KeyFactory fact = KeyFactory.getInstance("RSA")
      privateKey = fact.generatePrivate(keySpec)
    } catch (Exception e) {
      throw new RuntimeException("RSA Key Serialization Error", e)
    } finally {
      oin.close()
    }
    privateKey
  }


  /* Public/Private Key Generation Methods
  def doKeyGen() {

    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA")
    kpg.initialize(2048)
    KeyPair kp = kpg.genKeyPair()

    KeyFactory fact = KeyFactory.getInstance("RSA")
    RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(), RSAPublicKeySpec.class)
    RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(), RSAPrivateKeySpec.class)

    saveToFile("public.key", pub.getModulus(), pub.getPublicExponent())
    saveToFile("private.key", priv.getModulus(), priv.getPrivateExponent())
  }

  def saveToFile(String fileName, BigInteger mod, BigInteger exp) throws IOException {
    ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))
    try {
      oout.writeObject(mod)
      oout.writeObject(exp)
    } catch (Exception e) {
      throw new IOException("Unexpected error", e)
    } finally {
      oout.close()
    }
  }
  */
}
