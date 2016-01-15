package application;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;

import static org.hamcrest.CoreMatchers.is;
public class NodeLinkTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNodeLink() {
		AnchorPane anchorPane;
		anchorPane = new AnchorPane();
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/NodeLink.fxml")
				);

		fxmlLoader.setRoot(anchorPane);
		fxmlLoader.setController(anchorPane);

		try {
			fxmlLoader.load();

		} catch (IOException exception) {
		    throw new RuntimeException(exception);
		}
		anchorPane.setId(UUID.randomUUID().toString());
		assertNotNull("should not be null",anchorPane.getId());
		//System.out.println(fxmlLoader.getClassLoader().toString());
	}



	@Test
	public void testSetStart() {
		NodeLink nl = new NodeLink();
		nl.setStart(new Point2D(2, 4));
		//nl.node_link.getStartX();
		//System.out.println(nl.node_link.getStartX());
		assertThat(nl.node_link.getStartX(), is(2.0));
		assertThat(nl.node_link.getStartY(), is(4.0));
		
	}

	@Test
	public void testSetEnd() {
		
		NodeLink nl = new NodeLink();
		nl.setEnd(new Point2D(2, 4));

		assertThat(nl.node_link.getEndX(), is(2.0));
		assertThat(nl.node_link.getEndY(), is(4.0));
	}
	@Test
	public void testInitialize() {
		NodeLink nl = new NodeLink();
		nl.setStart(new Point2D(2, 4));
		//nl.node_link.getStartX();
		//System.out.println(nl.node_link.getStartX());
		
	}
	@Test
	public void testBindEnds() {
//		DraggableNode dn1 = new DraggableNode();
//		DraggableNode dn2 = new DraggableNode();
//		NodeLink nodelink = new NodeLink();
//		nodelink.bindEnds(dn1,dn2);
//		
		//nodelink.
	}

}
