package school.jiemian;

import school.functions.Ditu;
import school.functions.Jingdian;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 查询路径面板
 */
public class FindPathPanel extends JPanel {
    private MainFrame mainFrame;
    
    // 组件
    private JComboBox<String> startComboBox;
    private JComboBox<String> endComboBox;
    private JTextArea resultTextArea;
    
    /**
     * 构造函数
     * @param mainFrame 主窗口对象
     */
    public FindPathPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }
    
    /**
     * 初始化界面组件
     */
    private void initComponents() {
        // 设置布局
        setLayout(new BorderLayout());
        
        // 创建标题标签
        JLabel titleLabel = new JLabel("查询路径", JLabel.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        // 创建查询面板
        JPanel queryPanel = new JPanel(new GridBagLayout());
        queryPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 10, 15, 10);
        
        // 起始景点
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblStart = new JLabel("起始景点:");
        lblStart.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        queryPanel.add(lblStart, gbc);
        
        gbc.gridx = 1;
        // 创建起始景点下拉列表
        String[] jdItems = new String[MainFrame.scr.jd.size()];
        for (int i = 0; i < MainFrame.scr.jd.size(); i++) {
            Jingdian jd = MainFrame.scr.jd.get(i);
            jdItems[i] = String.format("%s - %s", jd.ID, jd.name);
        }
        startComboBox = new JComboBox<>(jdItems);
        startComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        queryPanel.add(startComboBox, gbc);
        
        // 目标景点
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblEnd = new JLabel("目标景点:");
        lblEnd.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        queryPanel.add(lblEnd, gbc);
        
        gbc.gridx = 1;
        endComboBox = new JComboBox<>(jdItems);
        endComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        // 默认选择下一个景点
        if (MainFrame.scr.jd.size() > 1) {
            endComboBox.setSelectedIndex(1);
        }
        queryPanel.add(endComboBox, gbc);
        
        // 查询按钮
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnFind = new JButton("查询最短路径");
        btnFind.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        queryPanel.add(btnFind, gbc);
        
        add(queryPanel, BorderLayout.NORTH);
        
        // 创建结果面板
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
        
        JLabel lblResult = new JLabel("查询结果:");
        lblResult.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        resultPanel.add(lblResult, BorderLayout.NORTH);
        
        // 创建结果文本区域
        resultTextArea = new JTextArea(10, 40);
        resultTextArea.setEditable(false);
        resultTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        resultTextArea.setLineWrap(true);
        resultTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(resultPanel, BorderLayout.CENTER);
        
        // 创建返回按钮
        JButton btnBack = new JButton("返回主菜单");
        btnBack.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // 添加按钮事件监听器
        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findPath();
            }
        });
        
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("MainMenu");
            }
        });
    }
    
    /**
     * 查询路径
     */
    private void findPath() {
        // 获取起始和目标景点
        String startItem = (String) startComboBox.getSelectedItem();
        String endItem = (String) endComboBox.getSelectedItem();
        
        if (startItem == null || endItem == null) {
            JOptionPane.showMessageDialog(this, "请选择景点", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // 提取景点编号
        String startId = startItem.split(" - ")[0];
        String endId = endItem.split(" - ")[0];
        
        // 不能选择相同的景点
        if (startId.equals(endId)) {
            JOptionPane.showMessageDialog(this, "起始景点和目标景点不能相同", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // 创建地图对象
            Ditu ditu = new Ditu(MainFrame.scr);
            
            // 检查景点是否存在
            if (!ditu.id2jd.containsKey(startId) || !ditu.id2jd.containsKey(endId)) {
                JOptionPane.showMessageDialog(this, "选择的景点不存在", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 使用Dijkstra算法查找最短路径
            Map<String, String> pathMap = ditu.Dijkstra(startId);
            
            // 构建路径
            List<String> path = new LinkedList<>();
            path.add(0, endId);
            for (String current = endId; !current.equals(startId);) {
                current = pathMap.get(current);
                if (current == null) {
                    break;
                }
                path.add(0, current);
            }
            
            // 显示结果
            if (path.size() < 2 || !path.get(0).equals(startId)) {
                resultTextArea.setText("无法找到从" + startItem + "到" + endItem + "的路径");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("从 %s 到 %s 的最短路径:\n\n", startItem, endItem));
                
                double totalDistance = 0.0;
                for (int i = 0; i < path.size(); i++) {
                    String id = path.get(i);
                    String name = ditu.id2jd.get(id).name;
                    
                    if (i == 0) {
                        sb.append(String.format("起点: %s (%s)\n", name, id));
                    } else {
                        String prevId = path.get(i - 1);
                        String key = String.format("%s##%s", prevId, id);
                        Double distanceDouble = MainFrame.scr.dt.get(key);
                        
                        // 如果正向距离不存在，尝试获取反向距离
                        if (distanceDouble == null) {
                            key = String.format("%s##%s", id, prevId);
                            distanceDouble = MainFrame.scr.dt.get(key);
                        }
                        
                        // 如果仍然找不到距离，使用0作为默认值
                        double distance = distanceDouble != null ? distanceDouble : 0.0;
                        totalDistance += distance;
                        
                        sb.append(String.format("→ %s (%s)  [距离: %.2f 米]\n", name, id, distance));
                    }
                }
                
                sb.append(String.format("\n总距离: %.2f 米\n", totalDistance));
                resultTextArea.setText(sb.toString());
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "查询出错: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
