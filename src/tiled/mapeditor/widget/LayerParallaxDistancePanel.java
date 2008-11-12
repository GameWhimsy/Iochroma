/*
 * LayerParallaxDistancePanel.java
 *
 * Created on 31 October 2008, 16:32
 */

package tiled.mapeditor.widget;

import javax.swing.border.TitledBorder;
import javax.swing.undo.UndoableEditSupport;
import tiled.core.MapLayer;
import tiled.core.MapParallaxChangeEvent;
import tiled.core.MapParallaxChangeListener;
import tiled.mapeditor.undo.MapLayerViewportSettingsEdit;

/**
 *
 * @author  upachler
 */
public class LayerParallaxDistancePanel extends javax.swing.JPanel {

	private MapLayer layer;
	private UndoableEditSupport undoSupport;
	private float rangeMin;
	private float rangeMax;
    
    private boolean distanceSliderPreviouslyAdjusting = false;
    private boolean nextEditIsSignificant = false;
    private MapParallaxChangeListener listener = new MapParallaxChangeListener() {

        public void parallaxParameterChanged(MapParallaxChangeEvent e) {
            switch(e.getChangeType()){
                case LAYER_VIEWPLANE_DISTANCE:  {
                    assert layer.getMap().getLayer(e.getLayerIndex()) == layer;
                    LayerParallaxDistancePanel.this.updateUIFromLayer();
                }   break;
            }
        }
    };
	
    /** Creates new form LayerParallaxDistancePanel */
    public LayerParallaxDistancePanel(MapLayer layer, UndoableEditSupport undoSupport, float rangeMin, float rangeMax) {
        initComponents();
		this.layer = layer;
		this.undoSupport = undoSupport;
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
		
        layer.getMap().addMapParallaxChangeListener(listener);
		try {
			((TitledBorder)getBorder()).setTitle(layer.getName());
		} catch(ClassCastException ccx){
		}
		updateUIFromLayer();
        		
    }
	
	public void setEditRange(float min, float max){
		
		rangeMin = min;
		rangeMax = max;
		updateUIFromLayer();
	}

    void detachFromMap() {
        layer.getMap().removeMapParallaxChangeListener(listener);
    }

	private void updateLayerFromDistanceSlider() {
		int intMin = distanceSlider.getMinimum();
		int intMax = distanceSlider.getMaximum();
		int intValue = distanceSlider.getValue();
		float epsilon = (rangeMax-rangeMin) / (intMax-intMin);
        
		float value = layer.getViewPlaneDistance();
        
		float newValue = (intValue-intMin) * (rangeMax-rangeMin) / (intMax-intMin) + rangeMin;
		if(java.lang.Math.abs(newValue-value) > epsilon){
            // find out if we're looking at the first of a series in adjustments
            
            undoSupport.postEdit(new MapLayerViewportSettingsEdit(layer, nextEditIsSignificant));
            nextEditIsSignificant = false;
			layer.setViewPlaneDistance(newValue);
            distanceTextField.setText(String.format("%2.2f", newValue));
		}     
	}
	
	void updateUIFromLayer(){
		int intMin = distanceSlider.getMinimum();
		int intMax = distanceSlider.getMaximum();
		float value = layer.getViewPlaneDistance();
		
		int intValue = intMin + (int)((intMax-intMin) * (value-rangeMin)/(rangeMax-rangeMin));
		distanceSlider.setValue(intValue);
		distanceTextField.setText(String.format("%2.2f", value));
		boolean infinity = layer.isViewPlaneInfinitelyFarAway();
		infinityCheckBox.setSelected(infinity);
	}
	
	private void updateLayerFromUI(){
		updateLayerFromDistanceSlider();
		
		boolean infinity = infinityCheckBox.isSelected();
        if(layer.isViewPlaneInfinitelyFarAway() == infinity)
            return;
        
        undoSupport.postEdit(new MapLayerViewportSettingsEdit(layer));    
		layer.setViewPlaneInfinitelyFarAway(infinity);
		
	}
	
	private void updateUIState(){
		boolean infinityOn = infinityCheckBox.isSelected();
		
		zeroButton.setEnabled(!infinityOn);
		distanceSlider.setEnabled(!infinityOn);
	}
	
	
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        infinityCheckBox = new javax.swing.JCheckBox();
        zeroButton = new javax.swing.JButton();
        distanceTextField = new javax.swing.JTextField();
        distanceSlider = new javax.swing.JSlider();

        setBorder(javax.swing.BorderFactory.createTitledBorder("<LayerName>"));
        setLayout(new java.awt.GridBagLayout());

        infinityCheckBox.setText("infinity");
        infinityCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                infinityCheckBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.weightx = 1.0;
        add(infinityCheckBox, gridBagConstraints);

        zeroButton.setText("Zero");
        zeroButton.setMargin(new java.awt.Insets(0, 8, 0, 8));
        zeroButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zeroButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.weightx = 1.0;
        add(zeroButton, gridBagConstraints);

        distanceTextField.setEditable(false);
        distanceTextField.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(distanceTextField, gridBagConstraints);

        distanceSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                distanceSliderStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 3.0;
        add(distanceSlider, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

private void infinityCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_infinityCheckBoxItemStateChanged
	updateUIState();
	updateLayerFromUI();	
}//GEN-LAST:event_infinityCheckBoxItemStateChanged

private void zeroButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zeroButtonActionPerformed
    undoSupport.postEdit(new MapLayerViewportSettingsEdit(layer));
    layer.setViewPlaneDistance(0);
	updateUIFromLayer();
}//GEN-LAST:event_zeroButtonActionPerformed

private void distanceSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_distanceSliderStateChanged
    // find out if the next edit is a significant one (important for the
    // undo manager and the MapLayerViewportSettingsEdit to find out if
    // edits need to be posted to the undo manager
    if(!distanceSliderPreviouslyAdjusting && distanceSlider.getValueIsAdjusting())
        nextEditIsSignificant = true;
    distanceSliderPreviouslyAdjusting = distanceSlider.getValueIsAdjusting();
    
    updateLayerFromDistanceSlider();    
}//GEN-LAST:event_distanceSliderStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSlider distanceSlider;
    private javax.swing.JTextField distanceTextField;
    private javax.swing.JCheckBox infinityCheckBox;
    private javax.swing.JButton zeroButton;
    // End of variables declaration//GEN-END:variables

}
