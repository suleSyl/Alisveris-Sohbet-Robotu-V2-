package ndpproje1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import redis.clients.jedis.Jedis;

public class MainPage extends javax.swing.JFrame {

    public MainPage() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        jLabel5.setVisible(false);                  // Swing components that become visible later when a category is selected
        jComboBox1.setBackground(Color.WHITE);
        botControl();
    }

    public void botControl() {
        // Predefined messages that may be used by both user and chat bot
        ArrayList<String> userGreeting = new ArrayList<String>(Arrays.asList("Merhabalar", "Merhaba", "Selam", "İyi günler", "Mrb", "Slm"));
        ArrayList<String> userChatContinuation = new ArrayList<String>(Arrays.asList("Nasılsınız", "Nasılsın", "Naber"));
        ArrayList<String> userChatContinuation2 = new ArrayList<String>(Arrays.asList("İyi", "Teşekkürler", "Sağol"));
        ArrayList<String> userGoodbye = new ArrayList<String>(Arrays.asList("Görüşürüz", "Hoşçakal", "Bye", "İyi günler"));
        ArrayList<String> robotGreeting = new ArrayList<String>(Arrays.asList("Merhabalar", "Merhaba", "Selam", "İyi günler"));
        ArrayList<String> robotChatContinuation = new ArrayList<String>(Arrays.asList("İyiyim, ya siz?", "İyiyim, siz nasılsınız?", "Teşekkürler, siz nasılsınız?", "Teşekkür ederim, siz?", "İyiyim, teşekkür ederim, siz nasılsınız?"));
        ArrayList<String> robotException = new ArrayList<String>(Arrays.asList("Lütfen tekrarlar misiniz?", "Anlayamadım maalesef.", "Anlamadım, tekrarlar mısınız?"));
        ArrayList<String> robotGoodbye = new ArrayList<String>(Arrays.asList("Güle güle", "Hoşçakalın", "Tekrar görüşmek üzere", "Görüşürüz", "Görüşmek üzere", "İyi günler"));

        myText.addActionListener(new ActionListener() {
            boolean electronicsSelected = false, whiteGoodsSelected = false;

            @Override
            public void actionPerformed(ActionEvent e) {

                String s = myText.getText();
                displayedText.append("Siz       : " + s + "\n");
                myText.setText("");
                if (checkUserMessage(userGreeting, s)) {
                    showRandomMessage(robotGreeting);
                } else if (checkUserMessage(userChatContinuation, s)) {
                    showRandomMessage(robotChatContinuation);
                } else if (checkUserMessage(userChatContinuation2, s) || s.contains("evet") || s.contains("Evet") || s.contains("İsterim")) {
                    displayedText.append("Robot : Hangi kategorideki ürünlerle ilgileniyorsunuz? \n");
                    displayedText.append("Robot : Elektronik \n");
                    displayedText.append("Robot : Beyaz Eşya \n");
                } else if (s.contains("Elektronik") || s.contains("elektronik")) {
                    electronicsSelected = true;
                    displayedText.append("Robot : İlgilendiğiniz ürünü öğrenebilir miyim? \n");
                    displayedText.append("Robot : 1. Cep Telefonu \n");
                    displayedText.append("Robot : 2. Laptop \n");
                    displayedText.append("Robot : 3. Yazıcı \n");
                    displayedText.append("Robot : 4. Tablet \n");
                    displayedText.append("Robot : 5. Televizyon \n");
                } else if (s.contains("Beyaz") || s.contains("beyaz")) {
                    whiteGoodsSelected = true;
                    displayedText.append("Robot : İlgilendiğiniz ürünü öğrenebilir miyim? \n");
                    displayedText.append("Robot : 1. Buzdolabı \n");
                    displayedText.append("Robot : 2. Bulaşık Makinesi \n");
                    displayedText.append("Robot : 3. Çamaşır Makinesi \n");
                    displayedText.append("Robot : 4. Kurutma Makinesi \n");
                } else if (s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") || s.equals("5")) {
                    if (electronicsSelected == true) {
                        showRankedElectronics(Integer.parseInt(s));
                        electronicsSelected = false;
                    } else if (whiteGoodsSelected == true) {
                        showRankedWhiteGoods(Integer.parseInt(s));
                        whiteGoodsSelected = false;
                    } else {
                        displayedText.append("Robot : Önce bir kategori seçmeniz gerekiyor. \n");
                    }
                } else if (checkUserMessage(userGoodbye, s) || s.contains("hayır") || s.contains("Hayır")) {
                    showRandomMessage(robotGoodbye);
                } else {
                    showRandomMessage(robotException);
                }
            }
        });
    }

    public void showRankedElectronics(int number) {

        switch (number) {             // Ranks products of electronics category according to their polarity values
            case 1:
                displayedText.append("Robot : Kullanıcı yorumlarına göre en beğenilen cep telefonu: \n");
                Collections.sort(CellPhone.getCellPhones());
                Collections.reverse(CellPhone.getCellPhones());
                for (CellPhone c : CellPhone.getCellPhones()) {
                    displayedText.append(new String().format("Robot : %s  %s  -  Sezgi Değeri: Toplam: %.3f, Ortalama: %.3f\n", c.getpBrand(), c.getpModel(), c.getcTotalPolarity(), c.getcAveragePolarity()));
                }
                break;
            case 2:
                displayedText.append("Robot : Kullanıcı yorumlarına göre en beğenilen laptop: \n");
                Collections.sort(Laptop.getLaptops());
                Collections.reverse(Laptop.getLaptops());
                for (Laptop l : Laptop.getLaptops()) {
                    displayedText.append(new String().format("Robot : %s  %s  -  Sezgi Değeri: Toplam: %.3f, Ortalama: %.3f\n", l.getpBrand(), l.getpModel(), l.getlTotalPolarity(), l.getlAveragePolarity()));
                }
                break;
            case 3:
                displayedText.append("Robot : Kullanıcı yorumlarına göre en beğenilen yazıcı: \n");
                Collections.sort(Printer.getPrinters());
                Collections.reverse(Printer.getPrinters());
                for (Printer p : Printer.getPrinters()) {
                    displayedText.append(new String().format("Robot : %s  %s  -  Sezgi Değeri: Toplam: %.3f, Ortalama: %.3f\n", p.getpBrand(), p.getpModel(), p.getpTotalPolarity(), p.getpAveragePolarity()));
                }
                break;
            case 4:
                displayedText.append("Robot : Kullanıcı yorumlarına göre en beğenilen tablet: \n");
                Collections.sort(Tablet.getTablets());
                Collections.reverse(Tablet.getTablets());
                for (Tablet t : Tablet.getTablets()) {
                    displayedText.append(new String().format("Robot : %s  %s  -  Sezgi Değeri: Toplam: %.3f, Ortalama: %.3f\n", t.getpBrand(), t.getpModel(), t.gettTotalPolarity(), t.gettAveragePolarity()));
                }
                break;
            case 5:
                displayedText.append("Robot : Kullanıcı yorumlarına göre en beğenilen televizyon: \n");
                Collections.sort(Television.getTelevisions());
                Collections.reverse(Television.getTelevisions());
                for (Television t : Television.getTelevisions()) {
                    displayedText.append(new String().format("Robot : %s  %s  -  Sezgi Değeri: Toplam: %.3f, Ortalama: %.3f\n", t.getpBrand(), t.getpModel(), t.gettTotalPolarity(), t.gettAveragePolarity()));
                }
                break;
            default:
        }
        displayedText.append("Robot : Yeni bir ürün seçmek ister misiniz?\n");
    }

    public void showRankedWhiteGoods(int number) {

        switch (number) {              // Ranks products of white goods category according to their polarity values
            case 1:
                displayedText.append("Robot : Kullanıcı yorumlarına göre en beğenilen buzdolabı: \n");
                Collections.sort(Fridge.getFridges());
                Collections.reverse(Fridge.getFridges());
                for (Fridge f : Fridge.getFridges()) {
                    displayedText.append(new String().format("Robot : %s  %s  -  Sezgi Değeri: Toplam: %.3f, Ortalama: %.3f\n", f.getpBrand(), f.getpModel(), f.getfTotalPolarity(), f.getfAveragePolarity()));
                }
                break;
            case 2:
                displayedText.append("Robot : Kullanıcı yorumlarına göre en beğenilen bulaşık makinesi: \n");
                Collections.sort(DishWasher.getDishWashers());
                Collections.reverse(DishWasher.getDishWashers());
                for (DishWasher d : DishWasher.getDishWashers()) {
                    displayedText.append(new String().format("Robot : %s  %s  -  Sezgi Değeri: Toplam: %.3f, Ortalama: %.3f\n", d.getpBrand(), d.getpModel(), d.getdTotalPolarity(), d.getdAveragePolarity()));
                }
                break;
            case 3:
                displayedText.append("Robot : Kullanıcı yorumlarına göre en beğenilen çamaşır makinesi: \n");
                Collections.sort(WashingMachine.getWashingMachines());
                Collections.reverse(WashingMachine.getWashingMachines());
                for (WashingMachine w : WashingMachine.getWashingMachines()) {
                    displayedText.append(new String().format("Robot : %s  %s  -  Sezgi Değeri: Toplam: %.3f, Ortalama: %.3f\n", w.getpBrand(), w.getpModel(), w.getwTotalPolarity(), w.getwAveragePolarity()));
                }
                break;
            case 4:
                displayedText.append("Robot : Kullanıcı yorumlarına göre en beğenilen kurutma makinesi: \n");
                Collections.sort(Dryer.getDryers());
                Collections.reverse(Dryer.getDryers());
                for (Dryer d : Dryer.getDryers()) {
                    displayedText.append(new String().format("Robot : %s  %s  -  Sezgi Değeri: Toplam: %.3f, Ortalama: %.3f\n", d.getpBrand(), d.getpModel(), d.getdTotalPolarity(), d.getdAveragePolarity()));
                }
                break;
        }
        displayedText.append("Robot : Yeni bir ürün seçmek ister misiniz? \n");
    }

    public void showRandomMessage(ArrayList<String> messages) {   // Chooses a random message as a reply from the chat bot

        int decider = (int) (Math.random() * messages.size());
        displayedText.append("Robot : " + messages.get(decider) + "\n");
    }

    public boolean checkUserMessage(ArrayList<String> messages, String userMessage) {  // Checks if user's message is one of the messages defined by us

        for (String s : messages) {  // Performing case-sensitive search
            if (userMessage.contains(s) || userMessage.contains(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        myText = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        displayedText = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat Bot");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(25, 51, 126));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));

        jPanel2.setBackground(new java.awt.Color(25, 51, 126));

        jButton1.setBackground(new java.awt.Color(25, 51, 126));
        jButton1.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Ürün Ekleme");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(25, 51, 126));
        jButton2.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Sohbet Robotu");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-speech-bubble (1).png"))); // NOI18N
        jLabel8.setToolTipText("");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-plus.png"))); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8-export.png"))); // NOI18N

        jButton4.setBackground(new java.awt.Color(25, 51, 126));
        jButton4.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Çıkış");
        jButton4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(jLabel8))
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jLabel7)))
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(96, 96, 96))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jPanel3.setLayout(new java.awt.CardLayout());

        jPanel5.setBackground(new java.awt.Color(250, 234, 239));

        myText.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        myText.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153)));
        myText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        displayedText.setEditable(false);
        displayedText.setColumns(20);
        displayedText.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        displayedText.setRows(5);
        displayedText.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153)));
        jScrollPane2.setViewportView(displayedText);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
                    .addComponent(myText))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(myText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel5, "card2");

        jPanel4.setBackground(new java.awt.Color(214, 225, 235));

        jTextField1.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153)));

        jRadioButton1.setBackground(new java.awt.Color(214, 225, 235));
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jRadioButton1.setText("Elektronik");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setBackground(new java.awt.Color(214, 225, 235));
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jRadioButton2.setText("Beyaz Eşya");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel1.setText("Lütfen eklemek istediğiniz ürün kategorisini seçiniz");

        jLabel2.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel2.setText("Ürün türünü seçiniz");

        jComboBox1.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jComboBox1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153)));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel3.setText("Marka bilgisini giriniz");

        jLabel4.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel4.setText("Model bilgisini giriniz");

        jTextField2.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jTextField2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153)));

        jLabel5.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel5.setText("Girmek istediğiniz ürüne dair,");

        jLabel6.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N

        jTextField3.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jTextField3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153), new java.awt.Color(0, 0, 153)));

        jButton5.setBackground(new java.awt.Color(25, 51, 126));
        jButton5.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Ürün Ekle");
        jButton5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel2)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton2))
                            .addComponent(jLabel1)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 326, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(2, 2, 2)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(2, 2, 2)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 248, Short.MAX_VALUE)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        jPanel3.add(jPanel4, "card2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(240, 240, 240))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        jPanel3.removeAll();                         //  dynamic panel
        jPanel3.repaint();
        jPanel3.revalidate();

        jPanel3.add(jPanel5);
        jPanel3.repaint();
        jPanel3.revalidate();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        jPanel3.removeAll();                         //  dynamic panel
        jPanel3.repaint();
        jPanel3.revalidate();

        jPanel3.add(jPanel4);
        jPanel3.repaint();
        jPanel3.revalidate();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        System.exit(0);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed

        jComboBox1.removeAllItems();                  // Adding item to combobox after 1st category is selected
        jComboBox1.addItem("Cep Telefonu");
        jComboBox1.addItem("Laptop");
        jComboBox1.addItem("Yazıcı");
        jComboBox1.addItem("Tablet");
        jComboBox1.addItem("Televizyon");
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed

        jComboBox1.removeAllItems();                      // Adding item to combobox after 2nd category is selected
        jComboBox1.addItem("Buzdolabı");
        jComboBox1.addItem("Bulaşık Makinesi");
        jComboBox1.addItem("Çamaşır Makinesi");
        jComboBox1.addItem("Kurutma Makinesi");
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

        String s = "";                                              // Shows a different informative message below each time a different product is selected
        if (jRadioButton1.isSelected() == true) {
            if (jComboBox1.getSelectedIndex() == 0) {
                s = "<html>Ekran Boyutu (inch)<br/>Kamera Çözünürlüğü (MP)<br/>Ön Kamera Çözünürlüğü (MP)<br/>Dahili Hafıza (GB)<br/>Ram (GB)<br/>Ücret (TL)<br/>";
                jLabel6.setText(s);
            } else if (jComboBox1.getSelectedIndex() == 1) {
                s = "<html>Ekran Boyutu (inch)<br/>İşletim Sistemi<br/>Disk Kapasitesi (GB)<br/>Ram (GB)<br/>Ücret (TL)<br/>";
                jLabel6.setText(s);
            } else if (jComboBox1.getSelectedIndex() == 2) {
                s = "<html>Kağıt Kapasitesi<br/>Kartuş Sayısı<br/>Ücret (TL)<br/";
                jLabel6.setText(s);
            } else if (jComboBox1.getSelectedIndex() == 3) {
                s = "<html>Ekran Boyutu (inch)<br/>Kamera Çözünürlüğü (MP)<br/>Dahili Hafıza (GB)<br/>Ram (GB)<br/>Ücret (TL)<br/>";
                jLabel6.setText(s);
            } else if (jComboBox1.getSelectedIndex() == 4) {
                s = "<html>Ekran Boyutu (inch)<br/>Görüntü Kalitesi<br/>HDMI Girişi<br/>USB Girişi<br/>Ağırlık (kg)<br/>Ücret (TL)<br/>";
                jLabel6.setText(s);
            }
        } else if (jRadioButton2.isSelected() == true) {
            if (jComboBox1.getSelectedIndex() == 0) {
                s = "<html>Ürün Tipi<br/>Enerji Sınıfı<br/>Ürün Hacmi (lt)<br/>Ücret (TL)<br/>";
                jLabel6.setText(s);
            } else if (jComboBox1.getSelectedIndex() == 1) {
                s = "<html>Kullanım Şekli<br/>Enerji Sınıfı<br/>Program Sayısı<br/>Ücret (TL)<br/>";
                jLabel6.setText(s);
            } else if (jComboBox1.getSelectedIndex() == 2) {
                s = "<html>Yıkama Kapasitesi (kg)<br/>Yıkama Ses Seviyesi (dB)<br/>Ücret (TL)<br/>";
                jLabel6.setText(s);
            } else if (jComboBox1.getSelectedIndex() == 3) {
                s = "<html>Kurutma Kapasitesi (kg)<br/>Enerji Sınıfı<br/>Ücret (TL)<br/>";
                jLabel6.setText(s);
            }
        }
        s += "<br/>özelliklerini aralarına birer boşluk koyarak giriniz</html>";
        jLabel5.setVisible(true);                         //  Items becoming visible after product info is specified
        jLabel6.setText(s);
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        try {
            addProductsFromTextField();
        } catch (IOException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void addProductsFromTextField() throws IOException {

        int selectedCategoryIndex = jComboBox1.getSelectedIndex();     // Adding new products  
        ProductFactory productFactory = new ProductFactory(jTextField1.getText(), jTextField2.getText());
        productFactory.setparsedProductInfo(jTextField3.getText().split(" "));
        productFactory.setArrayList(tweetsArrayListIfExists(jTextField2.getText()));
        Product product;
        switch (selectedCategoryIndex) {
            case 0:
                if (jRadioButton1.isSelected() == true) {
                    product = productFactory.factoryMethod("CellPhone");   // Using "Factory Design Pattern" 
                    CellPhone.getCellPhones().add((CellPhone) product);
                    JOptionPane.showMessageDialog(null, CellPhone.getCellPhones().get(CellPhone.getCellPhones().indexOf(product)).showCellPhoneInfo(), "Bilgi", JOptionPane.INFORMATION_MESSAGE);  // checks if the arraylist adds a specific item successfully -gets the index first, reaches the item using it and displays it-
                } else if (jRadioButton2.isSelected() == true) {
                    product = productFactory.factoryMethod("Fridge");
                    Fridge.getFridges().add((Fridge) product);
                    JOptionPane.showMessageDialog(null, Fridge.getFridges().get(Fridge.getFridges().indexOf(product)).showFridgeInfo(), "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            case 1:
                if (jRadioButton1.isSelected() == true) {
                    product = productFactory.factoryMethod("Laptop");
                    Laptop.getLaptops().add((Laptop) product);
                    JOptionPane.showMessageDialog(null, Laptop.getLaptops().get(Laptop.getLaptops().indexOf(product)).showLaptopInfo(), "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                } else if (jRadioButton2.isSelected() == true) {
                    product = productFactory.factoryMethod("DishWasher");
                    DishWasher.getDishWashers().add((DishWasher) product);
                    JOptionPane.showMessageDialog(null, DishWasher.getDishWashers().get(DishWasher.getDishWashers().indexOf(product)).showDishWasherInfo(), "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            case 2:
                if (jRadioButton1.isSelected() == true) {
                    product = productFactory.factoryMethod("Printer");
                    Printer.getPrinters().add((Printer) product);
                    JOptionPane.showMessageDialog(null, Printer.getPrinters().get(Printer.getPrinters().indexOf(product)).showPrinterInfo(), "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                } else if (jRadioButton2.isSelected() == true) {
                    product = productFactory.factoryMethod("WashingMachine");
                    WashingMachine.getWashingMachines().add((WashingMachine) product);
                    JOptionPane.showMessageDialog(null, WashingMachine.getWashingMachines().get(WashingMachine.getWashingMachines().indexOf(product)).showWashingMachineInfo(), "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            case 3:
                if (jRadioButton1.isSelected() == true) {
                    product = productFactory.factoryMethod("Tablet");
                    Tablet.getTablets().add((Tablet) product);
                    JOptionPane.showMessageDialog(null, Tablet.getTablets().get(Tablet.getTablets().indexOf(product)).showTabletInfo(), "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                } else if (jRadioButton2.isSelected() == true) {
                    product = productFactory.factoryMethod("Dryer");
                    Dryer.getDryers().add((Dryer) product);
                    JOptionPane.showMessageDialog(null, Dryer.getDryers().get(Dryer.getDryers().indexOf(product)).showDryerInfo(), "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            case 4:
                product = productFactory.factoryMethod("Television");
                Television.getTelevisions().add((Television) product);
                JOptionPane.showMessageDialog(null, Television.getTelevisions().get(Television.getTelevisions().indexOf(product)).showTelevisionInfo(), "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
    }

    public ArrayList<Tweet> tweetsArrayListIfExists(String model) throws FileNotFoundException, IOException {

        FileOperations f = new FileOperations();          // Checks if the object to be added is present in the file
        Jedis jedis = new Jedis("localhost");
//        HashMap<String, String> hashmap = f.createHashMap();
        HashMap<String, String> hashmap = SingletonSenticNet.GetInstance().getHashMap();
        BufferedReader reader = new BufferedReader(new FileReader("Products To Be Added.txt"));
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            String[] parsedProductInfo = line.split("\t");
            if (parsedProductInfo[3].equalsIgnoreCase(model)) {
                List<String> list = jedis.lrange(parsedProductInfo[parsedProductInfo.length - 1], 0, 9);
                ArrayList<Tweet> a = f.createTweetsArraylist(parsedProductInfo[parsedProductInfo.length - 1], list, hashmap);
                reader.close();
                return a;
            }
        }
        reader.close();
        return null;
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextArea displayedText;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField myText;
    // End of variables declaration//GEN-END:variables

}
