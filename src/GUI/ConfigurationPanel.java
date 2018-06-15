package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gameControl.dashBoard;


@SuppressWarnings("serial")
public class ConfigurationPanel extends JPanel{
	
	private JLabel lbAlgorithmLabel;
	private JComboBox cmbAlgorithmCombo;
	private JButton buttonSave;
	
	private ConfigurationModel model;
	private dashBoard callBack;
	
	public ConfigurationModel getConfigurationModel() {
		return model;
	}

	public void setConfigurationModel(ConfigurationModel configurationModel) {
		this.model = configurationModel;
		updateView();
	}
	
	private void updateView() {
		DefaultComboBoxModel algorithmModel = new DefaultComboBoxModel(model.getAlgorithmNames());
		cmbAlgorithmCombo.setModel(algorithmModel);

	}

	private void updateModelClicked(){
		convertAndUpdateModel();
		callBack.configurationUpdated(model);
	}

	private void convertAndUpdateModel() {
		model.setSelectedAlgorithmName((String)cmbAlgorithmCombo.getSelectedItem());
	}

	public ConfigurationPanel(dashBoard callBack){
		this.callBack = callBack;
		init();
	}
	

	
	public void init() 
	{
	   this.setBorder(BorderFactory.createTitledBorder( "Configuration Panel" ) );
	   GridBagLayout gbConfigPanel = new GridBagLayout();
	   GridBagConstraints gbcConfigPanel = new GridBagConstraints();
	   this.setLayout( gbConfigPanel );

	   Insets insets = new Insets(3, 4, 10, 4);
	   
	   lbAlgorithmLabel = new JLabel( "Algorithms    : "  );
	   gbcConfigPanel.gridx = 0;
	   gbcConfigPanel.gridy = 0;
	   gbcConfigPanel.gridwidth = 1;
	   gbcConfigPanel.gridheight = 1;
	   gbcConfigPanel.fill = GridBagConstraints.BOTH;
	   gbcConfigPanel.weightx = 1;
	   gbcConfigPanel.weighty = 1;
	   gbcConfigPanel.insets = insets;
	   gbcConfigPanel.anchor = GridBagConstraints.NORTH;
	   gbConfigPanel.setConstraints( lbAlgorithmLabel, gbcConfigPanel );
	   this.add( lbAlgorithmLabel );


	   cmbAlgorithmCombo = new JComboBox(  );
	   gbcConfigPanel.gridx = 1;
	   gbcConfigPanel.gridy = 0;
	   gbcConfigPanel.gridwidth = 1;
	   gbcConfigPanel.gridheight = 1;
	   gbcConfigPanel.fill = GridBagConstraints.BOTH;
	   gbcConfigPanel.weightx = 1;
	   gbcConfigPanel.weighty = 0;
	   gbcConfigPanel.insets = insets;
	   gbcConfigPanel.anchor = GridBagConstraints.NORTH;
	   gbConfigPanel.setConstraints( cmbAlgorithmCombo, gbcConfigPanel );
	   this.add( cmbAlgorithmCombo );

	   
	   
	   buttonSave = new JButton( "Save" );
	   gbcConfigPanel.gridx = 1;
	   gbcConfigPanel.gridy = 3;
	   gbcConfigPanel.gridwidth = 1;
	   gbcConfigPanel.gridheight = 1;
	   gbcConfigPanel.fill = GridBagConstraints.BOTH;
	   gbcConfigPanel.weightx = 1;
	   gbcConfigPanel.weighty = 0;
	   gbcConfigPanel.insets = insets;
	   gbcConfigPanel.anchor = GridBagConstraints.NORTH;
	   gbConfigPanel.setConstraints( buttonSave, gbcConfigPanel );
	   this.add( buttonSave );
	   
	   buttonSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				updateModelClicked();
			}		   
	   });
	   
	} 
	

//	public static void main(String[] args) {
//		ConfigurationPanel panel = new ConfigurationPanel();
//		panel.setConfigurationModel(new ConfigurationModel());
//		JFrame frame = new JFrame("Game Configuration");
//		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
//		frame.setContentPane( panel );
//		frame.pack();
//		frame.setResizable(false);
//		frame.setVisible( true );
//	}
}
