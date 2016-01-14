package application;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DragIconTest {
	DragIcon di;
	@Before
	public void setUp() throws Exception {
		di = new DragIcon();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetType() {

		
		di.setType(DragIconType.button_switch);
		assertEquals(DragIconType.button_switch, di.getType());
		
		di.setType(DragIconType.cell);
		assertEquals(DragIconType.cell, di.getType());
		
		di.setType(DragIconType.closed_switch);
		assertEquals(DragIconType.closed_switch, di.getType());
		
		di.setType(DragIconType.lamp);
		assertEquals(DragIconType.lamp, di.getType());
		
		di.setType(DragIconType.open_switch);
		assertEquals(DragIconType.open_switch, di.getType());
		
		di.setType(DragIconType.resistor);
		assertEquals(DragIconType.resistor, di.getType());
		
		di.setType(DragIconType.wire);
		assertEquals(DragIconType.wire, di.getType());
		
		di.setType(DragIconType.LED);
		assertEquals(DragIconType.LED, di.getType());
		
		
	}

	@Test
	public void testSetType() {
		
	
		//System.out.println(di.getStyleClass().toString());
		

		
		di.setType(DragIconType.button_switch);
		assertEquals("dragicon icon_button_switch", di.getStyleClass().toString());
		
		di.setType(DragIconType.cell);
		assertEquals("dragicon icon_cell", di.getStyleClass().toString());
		
		di.setType(DragIconType.closed_switch);
		assertEquals("dragicon icon_closed_switch", di.getStyleClass().toString());
		
		di.setType(DragIconType.cubic_curve);
		//assertEquals("dragicon icon_cubic_curve", di.getStyleClass().toString());
		
		di.setType(DragIconType.lamp);
		assertEquals("dragicon icon_lamp", di.getStyleClass().toString());
		
		di.setType(DragIconType.LED);
		assertEquals("dragicon icon_LED", di.getStyleClass().toString());
		
		
		di.setType(DragIconType.open_switch);
		assertEquals("dragicon icon_open_switch", di.getStyleClass().toString());
		
		di.setType(DragIconType.resistor);
		assertEquals("dragicon icon_resistor", di.getStyleClass().toString());
		
		di.setType(DragIconType.wire);
		assertEquals("dragicon wire", di.getStyleClass().toString());
		
		
	}

}
