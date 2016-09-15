package application;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class Copy
{
	public static void initCopy(String data)
	{
		Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		content.putString(data);
		clipboard.setContent(content);
	}
}
