package com.gpcompanion.ui;

import com.gpcompanion.race.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RaceUI extends JPanel {
    private boolean showGap = true;
    
    public RaceUI(RaceEngine engine) {
        Color bgColor = new Color(26, 26, 26);
        Color fgColor = new Color(220, 220, 220);
        Color neonYellow = new Color(200, 255, 0);
        
        setBackground(bgColor);
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        topPanel.setBackground(bgColor);
        
        JLabel lapLabel = new JLabel("LAP " + engine.getCurrentLap() + "/" + engine.getTotalLaps());
        lapLabel.setForeground(fgColor);
        
        JButton toggleGapBtn = new RoundedButton("INTERVAL / GAP", bgColor, fgColor, Color.GRAY);
        JButton startBtn = new RoundedButton("START", neonYellow, Color.BLACK, neonYellow);
        JButton pauseBtn = new RoundedButton("PAUSE", bgColor, fgColor, Color.GRAY);
        
        topPanel.add(lapLabel);
        topPanel.add(toggleGapBtn);
        topPanel.add(startBtn);
        topPanel.add(pauseBtn);
        add(topPanel, BorderLayout.NORTH);
        
        AbstractTableModel model = new AbstractTableModel() {
            public int getRowCount() { return engine.getStandings().size(); }
            public int getColumnCount() { return 5; }
            public Object getValueAt(int r, int c) {
                Standing s = engine.getStandings().get(r);
                return switch(c) {
                    case 0 -> s.position;
                    case 1 -> s.driver.name;
                    case 2 -> String.format("%.3f", s.lastLapTime);
                    case 3 -> showGap ? "+" + String.format("%.3f", s.gapToLeader) : "+" + String.format("%.3f", s.intervalToCarAhead);
                    case 4 -> s.currentTire;
                    default -> "";
                };
            }
            public String getColumnName(int c) {
                return new String[]{"#", "DRIVER", "LAST LAP", "GAP/INTERVAL", "TIRE"}[c];
            }
        };

        JTable table = new JTable(model);
        table.setBackground(bgColor);
        table.setForeground(fgColor);
        table.setGridColor(new Color(100, 100, 100));
        table.setShowGrid(false);
        table.setShowHorizontalLines(true);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowHeight(40);
        table.getTableHeader().setBackground(bgColor);
        table.getTableHeader().setForeground(Color.GRAY);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
        
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        leftRenderer.setBackground(bgColor);
        leftRenderer.setForeground(fgColor);
        leftRenderer.setBorder(new EmptyBorder(0, 15, 0, 0));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(bgColor);
        centerRenderer.setForeground(fgColor);
        
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                Standing s = engine.getStandings().get(row);
                setHorizontalAlignment(JLabel.CENTER);
                setBackground(bgColor);
                setForeground(fgColor);
                setBorder(BorderFactory.createMatteBorder(0, 6, 0, 0, s.driver.teamColor));
                return c;
            }
        });
        table.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(new TireRenderer(bgColor));
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(bgColor);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        add(scroll, BorderLayout.CENTER);
        
        toggleGapBtn.addActionListener(e -> {
            showGap = !showGap;
            table.getColumnModel().getColumn(3).setHeaderValue(showGap ? "GAP" : "INTERVAL");
            table.getTableHeader().repaint();
            model.fireTableDataChanged();
        });
        
        Timer timer = new Timer(1000, e -> engine.advanceLap());
        startBtn.addActionListener(e -> timer.start());
        pauseBtn.addActionListener(e -> timer.stop());
        
        engine.setListener(() -> {
            lapLabel.setText("LAP " + engine.getCurrentLap() + "/" + engine.getTotalLaps());
            model.fireTableDataChanged();
        });
    }

    class RoundedButton extends JButton {
        private Color bg, fg, border;
        public RoundedButton(String text, Color bg, Color fg, Color border) {
            super(text);
            this.bg = bg;
            this.fg = fg;
            this.border = border;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(fg);
            setFont(getFont().deriveFont(Font.BOLD));
            setPreferredSize(new Dimension(120, 30));
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 30, 30));
            g2.setColor(border);
            g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 30, 30));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    class TireRenderer extends JPanel implements javax.swing.table.TableCellRenderer {
        private String text = "";
        private Color bgColor;
        
        public TireRenderer(Color bg) {
            this.bgColor = bg;
            setOpaque(true);
            setBackground(bg);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            text = value != null ? value.toString() : "";
            return this;
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            Color pillColor = Color.WHITE;
            Color textColor = Color.BLACK;
            if (text.equals("Soft")) { pillColor = Color.RED; textColor = Color.WHITE; }
            else if (text.equals("Medium")) { pillColor = Color.YELLOW; textColor = Color.BLACK; }
            else if (text.equals("Hard")) { pillColor = Color.WHITE; textColor = Color.BLACK; }
            
            int w = 60, h = 24;
            int x = (getWidth() - w) / 2;
            int y = (getHeight() - h) / 2;
            
            g2.setColor(pillColor);
            g2.fill(new RoundRectangle2D.Float(x, y, w, h, h, h));
            
            g2.setColor(textColor);
            FontMetrics fm = g2.getFontMetrics();
            int stringWidth = fm.stringWidth(text);
            g2.drawString(text, x + (w - stringWidth) / 2, y + ((h - fm.getHeight()) / 2) + fm.getAscent());
            
            g2.setColor(new Color(100, 100, 100));
            g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            
            g2.dispose();
        }
    }
}
