import javax.swing.*;
import java.awt.*;

public class DrawPlayer extends JLabel implements View {
	private double scale;
	
	public DrawPlayer(double textSize) {
		super("");
		if (textSize == 0) {
			scale = javafx.scene.text.Font.getDefault().getSize();
		}
		else {
			scale = textSize;
		}
		Font originalFont = getFont();
		setFont(originalFont.deriveFont((float) scale));
	}
	@Override
	public void update(Object arg) {
		if (arg instanceof String) {
			setText((String) arg);
		}
	}
}
