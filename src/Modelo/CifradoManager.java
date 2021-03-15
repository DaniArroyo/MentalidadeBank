package Modelo;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CifradoManager {

	private SecretKey key;
	private Cipher cipher;
	private String algoritmo = "AES";

	public static String cifrarTextoHash(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());
		String textoCifrado = new String(md.digest());
		return textoCifrado;
	}

	public void addKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		key = keyGenerator.generateKey();
	}

	private String cifrarDatosSimetrico(String mensaje) {
		String mensajeCifrado = "";
		try {
			cipher = Cipher.getInstance(algoritmo);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] textobytes = mensaje.getBytes();
			byte[] cipherbytes = cipher.doFinal(textobytes);
			byte[] mensajeC = Base64.getEncoder().encode(cipherbytes);
			mensajeCifrado = new String(mensajeC);
		} catch (NoSuchAlgorithmException ex) {
			System.err.println(ex.getMessage());
		} catch (NoSuchPaddingException ex) {
			System.err.println(ex.getMessage());
		} catch (InvalidKeyException ex) {
			System.err.println(ex.getMessage());
		} catch (IllegalBlockSizeException ex) {
			System.err.println(ex.getMessage());
		} catch (BadPaddingException ex) {
			System.err.println(ex.getMessage());
		}
		return mensajeCifrado;
	}

	public Transferencia cifradoSimetricoTransanccion(String idTransaccion, String usuarioReceptor,
			String cantidadTotal, DateFormat fecha) throws NoSuchAlgorithmException {
		addKey();
		Date date = Calendar.getInstance().getTime();
		String strFecha = fecha.format(date);
		cantidadTotal = cifrarDatosSimetrico(cantidadTotal);
		String fechaString = cifrarDatosSimetrico(strFecha);
		idTransaccion = cifrarDatosSimetrico(idTransaccion);
		String llave = Base64.getEncoder().encodeToString(key.getEncoded());
		Transferencia transaccionCifrada = new Transferencia(idTransaccion, usuarioReceptor, cantidadTotal, fechaString,
				llave);
		return transaccionCifrada;
	}

	public String descifrarDatosSimetrico(String mensaje, String key) {
		String mensajeCifrado = "";
		try {

			byte[] decodedKey = Base64.getDecoder().decode(key);
			SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

			byte[] value = Base64.getDecoder().decode(mensaje);
			cipher = Cipher.getInstance(algoritmo);
			cipher.init(Cipher.DECRYPT_MODE, originalKey);

			byte[] cipherbytes = cipher.doFinal(value);
			mensajeCifrado = new String(cipherbytes);

		} catch (InvalidKeyException ex) {
			System.err.println(ex.getMessage());
		} catch (IllegalBlockSizeException ex) {
			System.err.println(ex.getMessage());
		} catch (BadPaddingException ex) {
			System.err.println(ex.getMessage());
		} catch (NoSuchAlgorithmException ex) {
			System.err.println(ex.getMessage());
		} catch (NoSuchPaddingException ex) {
			System.err.println(ex.getMessage());
		}

		return mensajeCifrado;
	}

}
