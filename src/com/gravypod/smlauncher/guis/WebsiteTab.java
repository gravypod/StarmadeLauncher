package com.gravypod.smlauncher.guis;

import java.net.URL;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class WebsiteTab extends JScrollPane {
	
	/**
     * 
     */
    private static final long serialVersionUID = 8833393871974586107L;
	private final JTextPane blog = new JTextPane();
	
	public WebsiteTab() {
	
		this.blog.setEditable(false);
		this.blog.setMargin(null);
		this.blog.setContentType("text/html");
		this.blog.addHyperlinkListener(new HyperlinkListener() {
			
			public void hyperlinkUpdate(HyperlinkEvent he) {
			
				if (he.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
					try {
						java.awt.Desktop.getDesktop().browse(he.getURL().toURI());
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		});
		setViewportView(this.blog);
	}
	
	public void setPage(final String url) {
	
		Thread thread = new Thread("Update website tab") {
			
			public void run() {
			
				try {
					blog.setPage(new URL(url));
				} catch (Exception e) {
					e.printStackTrace();
					blog.setText("<html><body><br><br><br><br><br><br><br><center><h1>Error Loading Page!</h1><br>" + e.toString() + "</center></font></body></html>");
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
	}
	
}
