package application;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.util.Pair;

public class DragContainerTest {
	DragContainer dc ;
	@Before
	public void setUp() throws Exception {
		dc = new DragContainer();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddData() {
		assertEquals(true,dc.getData().isEmpty());
		dc.addData("key", "value");
		assertEquals(false,dc.getData().isEmpty());
	}

	@Test
	public void testGetValue() {
		
		dc.addData("key1", "value1");
		dc.addData("key2", "value2");
		dc.addData("key3", "value3");
		assertEquals("value2",dc.getValue("key2"));
	}

	@Test
	public void testGetData() {
		dc.getData().add(new Pair("key4", "value4"));
		assertEquals("value4",dc.getValue("key4"));
	}

}
