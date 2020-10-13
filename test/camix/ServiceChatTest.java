package camix;

import java.lang.reflect.Field;
import java.util.Hashtable;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class ServiceChatTest extends TestCase {
	ClientChat chatMock;
	@Before
	public void setUp(){
		chatMock = EasyMock.createMock(ClientChat.class);
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void testInformeDepartClient(){
		String surnom = "UnSurnomToutMignon";
		ServiceChat serviceChat = new ServiceChat("canalTestInformeDepartClient");
		EasyMock.expect(this.chatMock.donneSurnom()).andReturn(surnom);
		chatMock.envoieContacts(String.format(ProtocoleChat.MESSAGE_DEPART_CHAT, surnom));
		EasyMock.replay(this.chatMock);
		serviceChat.informeDepartClient(this.chatMock);
		EasyMock.verify(this.chatMock);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAjouteCanal(){
		ServiceChat serviceChat = new ServiceChat("canalTestAjouteCanal");
		final String nomCanal = "canalInsert";
		final String message = String.format(ProtocoleChat.MESSAGE_CREATION_CANAL, nomCanal);
		Field field;
		
		this.chatMock.envoieMessage(message);
		EasyMock.replay(this.chatMock);
		serviceChat.ajouteCanal(this.chatMock, nomCanal);
		
		try {
			field = ServiceChat.class.getDeclaredField("canaux");
			field.setAccessible(true);
			Hashtable<String, CanalChat> res = ((Hashtable<String, CanalChat>)field.get(serviceChat));
			assertEquals(2, res.size());
			assertNotNull(res.get(nomCanal));
		} catch (NoSuchFieldException e) {
			fail("Mauvais cracker détecté");
		} catch (SecurityException e) {
			fail("Mauvais cracker détecté");
		} catch (IllegalArgumentException e) {
			fail("Mauvais cracker détecté");
		} catch (IllegalAccessException e) {
			fail("Mauvais cracker détecté");
		}
		EasyMock.verify(this.chatMock);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAjouteCanalDoublon(){
		ServiceChat serviceChat = new ServiceChat("canalTestAjouteCanalDoublon");
		final String nomCanal = "canalDoublon";
		final String messageAcceptant = String.format(ProtocoleChat.MESSAGE_CREATION_CANAL, nomCanal);
		final String messageErreur = String.format(ProtocoleChat.MESSAGE_CREATION_IMPOSSIBLE_CANAL, nomCanal);
		Field field;
		
		this.chatMock.envoieMessage(messageAcceptant);
		this.chatMock.envoieMessage(messageErreur);
		
		EasyMock.replay(this.chatMock);
		
		serviceChat.ajouteCanal(this.chatMock, nomCanal);
		serviceChat.ajouteCanal(this.chatMock, nomCanal);
		try {
			field = ServiceChat.class.getDeclaredField("canaux");
			field.setAccessible(true);
			Hashtable<String, CanalChat> res = ((Hashtable<String, CanalChat>)field.get(serviceChat));
			assertEquals(2, res.size());
			assertNotNull(res.get(nomCanal));
		} catch (NoSuchFieldException e) {
			fail("Mauvais cracker détecté");
		} catch (SecurityException e) {
			fail("Mauvais cracker détecté");
		} catch (IllegalArgumentException e) {
			fail("Mauvais cracker détecté");
		} catch (IllegalAccessException e) {
			fail("Mauvais cracker détecté");
		}
		EasyMock.verify(this.chatMock);
	}
}
