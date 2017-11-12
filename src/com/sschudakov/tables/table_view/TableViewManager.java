package com.sschudakov.tables.table_view;

import com.sschudakov.gui.GBC;
import com.sschudakov.tables.expression_parsing.*;
import com.sschudakov.tables.expression_parsing.tokens.Token;
import com.sschudakov.tables.utils.TableSaver;
import com.sschudakov.tables.utils.TableViewCloser;
import com.sschudakov.utils.ExceptionRenderer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Semen Chudakov on 05.11.2017.
 */
public class TableViewManager {
    private JFrame frame = new JFrame("Table Viewer");
    private JPanel tablePanel = new JPanel();
    private JPanel labelsPanel = new JPanel();
    private JMenuBar menuBar = new JMenuBar();


    private JMenu fileMenu = new JMenu("File");
    private JMenu operationsMenu = new JMenu("Operation");

    private JMenuItem saveItem = new JMenuItem("save");
    private JMenuItem addRowItem = new JMenuItem("add row");
    private JMenuItem addColumnItem = new JMenuItem("add column");
    private JMenuItem removeRowItem = new JMenuItem("remove row");
    private JMenuItem removeColumnItem = new JMenuItem("remove column");


    private JTextField expressionLabel = new JTextField();
    private JTextField rowTextFiled = new JTextField();
    private JTextField columnTextField = new JTextField();

    private JTable table;
    private TableModel tableModel;
    private JScrollPane tableScrollPane;

    private LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
    private SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer);
    private ExpressionTree expressionTree;

    public TableViewManager(JTable table) {
        this.table = table;
        this.tableModel = (TableModel) table.getModel();
        this.tableScrollPane = new JScrollPane(table);
        this.expressionTree = new ExpressionTree(this.tableModel);
    }

    public void buildTableView() {

        setupFrame();

        setupPanels();

        setupMenuBar();

        addListeners();

        setupScrollPane();

        setupTextFields();

        setupTable();

        this.frame.setVisible(true);
    }

    private void setupFrame() {


        int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();

        this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.frame.setLocationByPlatform(true);
        this.frame.setLayout(new GridBagLayout());
        this.frame.setSize(screenWidth * 8 / 10, screenHeight * 8 / 10);

        this.frame.add(this.tablePanel, new GBC(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH));
        this.frame.add(this.labelsPanel, new GBC(0, 1, 1, 1, 0.2, 0.2, GridBagConstraints.BOTH));

        this.frame.setJMenuBar(this.menuBar);
    }

    private void setupPanels() {

        GridBagLayout tablePanelLayout = new GridBagLayout();

        this.tablePanel.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(Color.BLACK, 1, true), "Table"));
        this.tablePanel.setLayout(tablePanelLayout);
        this.tablePanel.add(this.tableScrollPane,
                new GBC(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH));

        GridBagLayout labelsPanelLayout = new GridBagLayout();

        this.labelsPanel.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(Color.BLACK, 1, true), "Text Fields"));
        this.labelsPanel.setLayout(labelsPanelLayout);
        this.labelsPanel.add(this.rowTextFiled,
                new GBC(0, 0, 1, 1, 0.2, 0.2, GridBagConstraints.BOTH));
        this.labelsPanel.add(this.columnTextField,
                new GBC(1, 0, 1, 1, 0.2, 0.2, GridBagConstraints.BOTH));
        this.labelsPanel.add(this.expressionLabel,
                new GBC(2, 0, 1, 1, 0.8, 0.8, GridBagConstraints.BOTH));
    }

    private void setupMenuBar() {

        this.fileMenu.add(this.saveItem);

        this.operationsMenu.add(this.addRowItem);
        this.operationsMenu.add(this.addColumnItem);
        this.operationsMenu.add(this.removeRowItem);
        this.operationsMenu.add(this.removeColumnItem);

        this.menuBar.add(this.fileMenu);
        this.menuBar.add(this.operationsMenu);
    }

    private void addListeners() {

        this.frame.addWindowListener(new CloseOperationListener());

        this.saveItem.addActionListener(new SaveListener());

        this.addRowItem.addActionListener(new AddRowListener());
        this.addColumnItem.addActionListener(new AddColumnListener());
        this.removeRowItem.addActionListener(new RemoveRowListener());
        this.removeColumnItem.addActionListener(new RemoveColumnListener());
    }

    private void setupScrollPane() {
        this.tableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.tableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    private void setupTextFields() {
        this.expressionLabel.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(Color.BLACK, 1, true), "Expression"));
        this.rowTextFiled.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(Color.BLACK, 1, true), "Row"));
        this.columnTextField.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(Color.BLACK, 1, true), "Column"));
    }

    private void setupTable() {
        this.table.setAutoCreateRowSorter(true);
        this.table.setCellSelectionEnabled(true);
        this.tableModel.addTableModelListener(new TableListener());
        System.out.println(Arrays.toString(this.tableModel.getListeners(TableModelListener.class)));
    }


    public class TableListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (e.getType() == TableModelEvent.UPDATE && row >= 0 && column >= 0) {

                System.out.println("row: " + row);
                System.out.println("column: " + column);
                System.out.println("type: " + e.getType());

                Object renewedValue = tableModel.getValueAt(row, column);

                if (renewedValue instanceof String) {
                    String expression = (String) tableModel.getValueAt(row, column);
                    System.out.println("\nexpression: " + expression + "\n");
                    TableCell cell = new TableCell();
                    try {

                        lexicalAnalyzer.setExpression(new Expression(expression));
                        Token parsedExpression = syntaxAnalyzer.expression();
                        expressionTree.setHead(parsedExpression);

                        Object value = expressionTree.evaluate();
                        cell.setValue(value.toString());
                        cell.setExpression(parsedExpression);
                        table.setValueAt(cell, row, column);
                    } catch (IllegalArgumentException e1) {
                        e1.printStackTrace();
                        ExceptionRenderer.renderException(frame, e1);
                    }
                }
            }
        }
    }


    class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            TableSaver.saveTable(table);
        }
    }

    class CloseOperationListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {

            System.out.println("Close operation");

            if (TableViewCloser.closeTableView(table)) {
                System.out.println("table closed");
//                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                frame.setVisible(false);
            }
        }
    }

    class AddRowListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("action performed");
            tableModel.addRow(formEmptyRowData());
        }

        private Object[] formEmptyRowData() {
            Object[] result = new Object[tableModel.getColumnCount()];
            for (int i = 0; i < result.length; i++) {
                result[i] = new TableCell();
            }
            return result;
        }
    }

    class AddColumnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            tableModel.addColumn(tableModel.getColumnName(tableModel.getColumnCount()));
        }
    }

    class RemoveRowListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] rows = table.getSelectedRows();
            for (int i = 0; i < rows.length; i++) {
                //to let removeRow work correctly
                tableModel.removeRow(rows[i] - i);
            }
        }
    }

    class RemoveColumnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] columns = table.getSelectedColumns();
            for (int i = 0; i < columns.length; i++) {
                //to let removeColumn work correctly
                tableModel.removeColumn(columns[i] - i);
            }
        }
    }


}
