import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.algorithms.generators.random.ErdosRenyiGenerator;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

public class visualizer {
    static Graph<Integer, String> g;
    static int nodeCount;
	static int edgeCount;
    static Factory<Integer> vertexFactory;
    static Factory<String> edgeFactory;
    static Factory<UndirectedGraph<Integer, String>> graphFactory;
	public static void main(String[] args) {
		// -------------------SET UP SUBMISSION INFORMATION----------------------
		JLabel label = new JLabel();		
		label.setText("Input Number of Nodes: ");
		label.setBounds(10, 10, 190, 30);
		JTextField field = new JTextField();
		field.setBounds(210, 10, 80, 30);
		
		JLabel labelProbability = new JLabel();		
		labelProbability.setText("Input Edge Probability: ");
		labelProbability.setBounds(10, 50, 190, 30);
		
		JTextField probabilityInput = new JTextField();
		probabilityInput.setBounds(210, 50, 80, 30);
		
		JPanel submitPanel = new JPanel();
		submitPanel.setBounds(100, 100, 100, 50);
		
		JButton submit=new JButton("Generate");
		submitPanel.add(submit);
		// -------------------END SUBMISSION INFORMATION----------------------

		// -------------------SET UP THRESHOLD INFORMATION-----------------------
		JLabel edgeThresholdInfo = new JLabel();
		edgeThresholdInfo.setText("Threshold of an Edge");
		edgeThresholdInfo.setBounds(75, 150, 200, 20);
		
		JLabel oEdgeInfo = new JLabel();
		oEdgeInfo.setText("Graph is Disconnected: o(n^-2)");
		oEdgeInfo.setBounds(10, 170, 200, 20);
		
		JLabel wEdgeInfo = new JLabel();
		wEdgeInfo.setText("Graph is Connected: w(n^-2)");
		wEdgeInfo.setBounds(10, 190, 200, 20);
		
		JLabel componentThresholdInfo = new JLabel();
		componentThresholdInfo.setText("Threshold of a Component>2");
		componentThresholdInfo.setBounds(50, 220, 200, 20);
		
		JLabel oComponentInfo = new JLabel();
		oComponentInfo.setText("Graph has no Component>2: o(n^-3/2)");
		oComponentInfo.setBounds(10, 240, 290, 20);
		
		JLabel wComponentInfo = new JLabel();
		wComponentInfo.setText("Graph has a Component>2: w(n^-3/2)");
		wComponentInfo.setBounds(10, 260, 290, 20);
		
		JLabel cycleThresholdInfo = new JLabel();
		cycleThresholdInfo.setText("Threshold of a Cycle");
		cycleThresholdInfo.setBounds(75, 290, 200, 20);
		
		JLabel oCycleInfo = new JLabel();
		oCycleInfo.setText("Graph has no Cycles: o(n^-1)");
		oCycleInfo.setBounds(10, 310, 290, 20);
		
		JLabel wCycleInfo = new JLabel();
		wCycleInfo.setText("Graph has a Cycle: w(n^-1)");
		wCycleInfo.setBounds(10, 330, 290, 20);
		
		JLabel connectedThresholdInfo = new JLabel();
		connectedThresholdInfo.setText("Threshold of Connectedness");
		connectedThresholdInfo.setBounds(50, 360, 200, 20);
		
		JLabel oConnectedInfo = new JLabel();
		oConnectedInfo.setText("Graph is not Connected: o(n^(log(n)/n))");
		oConnectedInfo.setBounds(10, 380, 290, 20);
		
		JLabel wConnectedInfo = new JLabel();
		wConnectedInfo.setText("Graph is Connected: w(n^(log(n)/n))");
		wConnectedInfo.setBounds(10, 400, 290, 20);		
		// -------------------END THRESHOLD INFORMATION-----------------------
		
		JLabel calculator = new JLabel();
		calculator.setText("Threshold Calculator");
		calculator.setBounds(50, 450, 200, 20);
		
		JButton calculate=new JButton("Calculate");
		JPanel calculatePanel = new JPanel();
		calculatePanel.setBounds(65, 470, 100, 50);
		calculatePanel.add(calculate);
		

		// -------------------FRAME CREATION-----------------------
		JFrame input = new JFrame("Erdos-Renyi Graph Visualizer");
        input.setPreferredSize(new Dimension(1400,1200)); //Sets the viewing area size
		input.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		input.getContentPane().setLayout(null);
		input.add(label);
		input.add(field);
		input.add(probabilityInput);
		input.add(labelProbability);
		
		input.add(edgeThresholdInfo);
		input.add(oEdgeInfo);
		input.add(wEdgeInfo);
		
		input.add(componentThresholdInfo);
		input.add(oComponentInfo);
		input.add(wComponentInfo);
		
		input.add(cycleThresholdInfo);
		input.add(oCycleInfo);
		input.add(wCycleInfo);
		
		input.add(connectedThresholdInfo);
		input.add(oConnectedInfo);
		input.add(wConnectedInfo);
		
		input.add(calculator);
		input.add(calculatePanel);		
		
		input.add(submitPanel);
				input.pack();
		input.setVisible(true); 

	     
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				g = new SparseMultigraph<Integer, String>();
				nodeCount = 0; edgeCount = 0;
				UndirectedGraph<Integer, String> test = new UndirectedSparseGraph();
				graphFactory = new Factory<UndirectedGraph<Integer, String>>() {
					public UndirectedGraph<Integer, String> create() {
						return test;
					}
				};
				vertexFactory = new Factory<Integer>() {
					public Integer create() {
						return nodeCount++;
					}
				};
				edgeFactory = new Factory<String>() { 
					public String create() {
						return "E"+edgeCount++;
					}
				};
				Integer nodes = Integer.valueOf(field.getText());
				Double edgeProbability = Double.valueOf(probabilityInput.getText());
				ErdosRenyiGenerator<Integer, String> bey = new ErdosRenyiGenerator<Integer, String>(graphFactory, vertexFactory, edgeFactory, nodes, edgeProbability);

				Graph<Integer, String> svg = bey.create();
				Layout<Integer, String> layout = new ISOMLayout<Integer, String>(svg);
				layout.setSize(new Dimension(1100,750)); // sets the initial size of the space
				BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<Integer,String>(layout);
				vv.setPreferredSize(new Dimension(1100,750)); //Sets the viewing area size

				JPanel graphPanel = new JPanel();
				graphPanel.setBounds(300, 50, 1100, 750);
				
				JPanel infoPanel = new JPanel();
				infoPanel.setBounds(300, 10, 750, 50);
				
				JLabel thresholdLabel = calculateThreshold(nodes, edgeProbability);
				
				System.out.println(thresholdLabel.getText());
				infoPanel.add(thresholdLabel);
				graphPanel.add(vv);
				
				input.add(infoPanel);
				input.add(graphPanel);
				input.revalidate();
				input.pack();
			}
		});
		
		calculate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Integer nodes = Integer.valueOf(field.getText());

				JLabel edgeCalc = new JLabel();
				BigDecimal bd = new BigDecimal(Math.pow(nodes, -2));
				bd = bd.round(new MathContext(3));
				edgeCalc.setText("n^-2: " +bd.doubleValue());
				
				JLabel componentCalc = new JLabel();
				bd = new BigDecimal(Math.pow(nodes, -1.5));
				bd = bd.round(new MathContext(3));
				componentCalc.setText("n^-3/2: " + bd.doubleValue());
				
				JLabel cycleCalc = new JLabel();
				bd = new BigDecimal(Math.pow(nodes, -1));
				bd = bd.round(new MathContext(3));
				cycleCalc.setText("1/n: " + bd.doubleValue());
				
				JLabel connectedCalc = new JLabel();
				bd = new BigDecimal(Math.log(nodes)/nodes);
				bd = bd.round(new MathContext(3));
				connectedCalc.setText("log(n)/n: " + bd.doubleValue());
				
				JPanel calculator = new JPanel();
				calculator.setBounds(10, 500, 150, 100);
				calculator.add(edgeCalc);
				calculator.add(componentCalc);
				calculator.add(cycleCalc);
				calculator.add(connectedCalc);

				System.out.println(edgeCalc.getText());
				input.add(calculator);
				input.revalidate();
			}
		});
    }
	
	public static JLabel calculateThreshold(int nodes, double edgeProbability) {
		JLabel thresholdLabel = new JLabel();		

		double edgeThreshold = edgeProbability*Math.pow(nodes, 2);
		System.out.println(edgeThreshold);
		if (edgeThreshold < 1 ) {
			thresholdLabel.setText("Threshold: o(n^-2)" + "\t Expected Graph: No Edges                                         ");
		} else {
			double componentThreshold = edgeProbability*Math.pow(nodes, (1.5));
			if (componentThreshold < 1 ) {
				thresholdLabel.setText("Threshold: o(n^-(3/2))" + "\t Expected Graph: Edges but no Components>2                ");
			} else {
				double cycleThreshold = edgeProbability*nodes;
				if (cycleThreshold < 1 ) {
					thresholdLabel.setText("Threshold: o(n^-1)" + "\t Expected Graph: Components>2 but no cycles");
				} else {
					double connectedThreshold = edgeProbability*(nodes/Math.log(nodes));
					if (connectedThreshold < 1 ) {
						thresholdLabel.setText("Threshold: o(log(n)/n)" + "\t Expected Graph: Cycles but not connected                   ");
					} else {
						thresholdLabel.setText("Threshold: w(log(n)/n)" + "\t Expected Graph: Connected                                ");
					}
				}
			}
		}
		return thresholdLabel;
	}

}
