package camix;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class CanalChatTest extends TestCase{
	ClientChat clientMock;
	
	@Before
	public void setUp(){
		this.clientMock = EasyMock.createMock(ClientChat.class);
	}
	
	@After
	public void tearDown(){}
	
	@Test
	public void testAjouteClient(){
		CanalChat canal = new CanalChat("CanalTest");
		EasyMock.expect(
				this.clientMock.donneId()
				).andReturn("Id");
		EasyMock.expectLastCall().times(3);
		EasyMock.replay(this.clientMock);
		
		canal.ajouteClient(this.clientMock);
		
		assertEquals(new Integer(1), canal.donneNombreClients());
		assertTrue(canal.estPresent(clientMock));
		
		EasyMock.verify(clientMock);
	}
	
	@Test
	public void testAjoutDoublon(){
		CanalChat canal = new CanalChat("CanalTest");
		EasyMock.expect(
				this.clientMock.donneId()
				).andReturn("Id");
		EasyMock.expectLastCall().times(4);
		EasyMock.replay(this.clientMock);
		
		canal.ajouteClient(this.clientMock);
		canal.ajouteClient(this.clientMock);
		
		assertEquals(new Integer(1), canal.donneNombreClients());
		assertTrue(canal.estPresent(clientMock));
		
		EasyMock.verify(clientMock);
	}
}
