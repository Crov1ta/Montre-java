import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fenêtre principale de l'application Montre.
 * Navigation entre 5 pages via une barre de boutons en bas.
 *
 * @author Mohamed
 * @version 3.0
 */
public class MainFrame extends JFrame {

    private WatchModel     modele;
    private ReglagesMontre reglages;
    private WatchPanel     watchPanel;
    private Timer          timer;

    // Navigation
    private CardLayout cardLayout;
    private JPanel     container;

    // Boutons de navigation
    private JButton btnMontre;
    private JButton btnDate;
    private JButton btnRythme;
    private JButton btnBatterie;
    private JButton btnReglages;


    // Boutons radio secondes
    private JRadioButton rbTrotteuse;
    private JRadioButton rbMiniCadran;
    private JRadioButton rbCompteur;

    // Éléments des pages secondaires
    private DateDisplay     dateDisplay;
    private RythmeCardiaque rythmeCardiaque;
    private EtatBatterie    etatBatterie;

    public MainFrame(WatchModel modele, ReglagesMontre reglages) {
        this.modele   = modele;
        this.reglages = reglages;

        setTitle("Montre connectée");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        container  = new JPanel(cardLayout);

        container.add(creerPageMontre(),    "montre");
        container.add(creerPageDate(),      "date");
        container.add(creerPageRythme(),    "rythme");
        container.add(creerPageBatterie(),  "batterie");
        container.add(creerPageReglages(),  "reglages");

        add(container,       BorderLayout.CENTER);
        add(creerBarreNav(), BorderLayout.SOUTH);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.time.LocalTime now = java.time.LocalTime.now();
                MainFrame.this.modele.tick();
                rythmeCardiaque.update(now);
                etatBatterie.update(now);
                watchPanel.repaint();
                container.repaint();
            }
        });
        timer.start();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //  BARRE DE NAVIGATION
   
    private JPanel creerBarreNav() {
        JPanel barre = new JPanel(new GridLayout(1, 5));
        barre.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        btnMontre   = new JButton("Montre");
        btnDate     = new JButton("Date");
        btnRythme   = new JButton("Rythme");
        btnBatterie = new JButton("Batterie");
        btnReglages = new JButton("Réglages");

        mettreEnGras(btnMontre);

        btnMontre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(container, "montre");
                mettreEnGras(btnMontre);
            }
        });

        btnDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(container, "date");
                mettreEnGras(btnDate);
            }
        });

        btnRythme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(container, "rythme");
                mettreEnGras(btnRythme);
            }
        });

        btnBatterie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(container, "batterie");
                mettreEnGras(btnBatterie);
            }
        });

        btnReglages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(container, "reglages");
                mettreEnGras(btnReglages);
            }
        });

        barre.add(btnMontre);
        barre.add(btnDate);
        barre.add(btnRythme);
        barre.add(btnBatterie);
        barre.add(btnReglages);

        return barre;
    }

    private void mettreEnGras(JButton actif) {
        JButton[] tous = { btnMontre, btnDate, btnRythme, btnBatterie, btnReglages };
        for (JButton btn : tous) {
            btn.setFont(btn.getFont().deriveFont(Font.PLAIN));
        }
        actif.setFont(actif.getFont().deriveFont(Font.BOLD));
    }

    // =========================================================
    //  PAGE 1 — Montre
    // =========================================================

    private JPanel creerPageMontre() {
        JPanel page = new JPanel(new BorderLayout());

        watchPanel = new WatchPanel(modele, reglages);
        page.add(watchPanel, BorderLayout.CENTER);
        page.add(creerPanneauSecondes(), BorderLayout.SOUTH);

        return page;
    }

    private JPanel creerPanneauSecondes() {
        JPanel panel = new JPanel();

        rbTrotteuse  = new JRadioButton("Trotteuse", true);
        rbMiniCadran = new JRadioButton("Mini cadran");
        rbCompteur   = new JRadioButton("Compteur numérique");

        ButtonGroup groupe = new ButtonGroup();
        groupe.add(rbTrotteuse);
        groupe.add(rbMiniCadran);
        groupe.add(rbCompteur);

        rbTrotteuse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { mettreAJourSecondes(); }
        });
        rbMiniCadran.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { mettreAJourSecondes(); }
        });
        rbCompteur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { mettreAJourSecondes(); }
        });
        JButton btnSimuler = new JButton("Heure personnalisée");
        btnSimuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tfH = new JTextField("12", 2);
                JTextField tfM = new JTextField("00", 2);
                JTextField tfS = new JTextField("00", 2);

                JPanel p = new JPanel();
                p.add(new JLabel("H:")); p.add(tfH);
                p.add(new JLabel("M:")); p.add(tfM);
                p.add(new JLabel("S:")); p.add(tfS);

                int result = JOptionPane.showConfirmDialog(null, p,
                    "Heure de départ", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int h = Integer.parseInt(tfH.getText().trim());
                        int m = Integer.parseInt(tfM.getText().trim());
                        int s = Integer.parseInt(tfS.getText().trim());
                        modele.demarrerSimulation(h, m, s);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Heure invalide.");
                    }
                }
            }
        });

        panel.add(new JLabel("Secondes : "));
        panel.add(rbTrotteuse);
        panel.add(rbMiniCadran);
        panel.add(rbCompteur);
        panel.add(btnSimuler); 

        return panel;
    }

private void appliquerFacteurEpaisseur(float facteur) {
    for (ElementMontre el : modele.getElements()) {
        if (el instanceof Aiguille) {
            ((Aiguille) el).setFacteurEpaisseur(facteur);
        }
    }
    watchPanel.repaint();
}

private void mettreAJourSecondes() {
    modele.retirerElementType(AiguilleSecondes.class);
    modele.retirerElementType(MiniCadranSecondes.class);
    modele.retirerElementType(CompteurSecondes.class);

    if (rbTrotteuse.isSelected()) {
        AiguilleSecondes as = new AiguilleSecondes();
        as.setCouleur(reglages.getCouleurAiguilles());
        as.setFacteurEpaisseur(reglages.getFacteurEpaisseur());
        modele.ajouterElement(as);

    } else if (rbMiniCadran.isSelected()) {
        // On insère le mini cadran EN PREMIER (index 0)
        modele.ajouterElementEnPremier(new MiniCadranSecondes(reglages));

    } else if (rbCompteur.isSelected()) {
        modele.ajouterElement(new CompteurSecondes());
    }

    watchPanel.repaint();
}

    // =========================================================
    //  PAGE 2 — Date
    // =========================================================

    private JPanel creerPageDate() {
        dateDisplay = new DateDisplay();

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(Color.BLACK);

        JPanel dessin = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                     RenderingHints.VALUE_ANTIALIAS_ON);
                dateDisplay.update(java.time.LocalTime.now());
                dateDisplay.drawPage(g2d, getWidth() / 2, getHeight() / 2);
            }
        };
        dessin.setBackground(Color.BLACK);
        dessin.setPreferredSize(new Dimension(350, 350));

        // --- Sélecteur de format ---
        JComboBox<DateDisplay.FormatDate> comboFormat =
                new JComboBox<>(DateDisplay.FormatDate.values());
        comboFormat.setSelectedItem(DateDisplay.FormatDate.COURT);
        comboFormat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateDisplay.FormatDate fmt =
                        (DateDisplay.FormatDate) comboFormat.getSelectedItem();
                dateDisplay.setFormat(fmt);
                dessin.repaint();
            }
        });

        JPanel barreFormat = new JPanel();
        barreFormat.setBackground(Color.BLACK);
        barreFormat.add(new JLabel("Format : ") {{
            setForeground(Color.WHITE);
        }});
        barreFormat.add(comboFormat);

        page.add(dessin,       BorderLayout.CENTER);
        page.add(barreFormat,  BorderLayout.SOUTH);
        return page;
    }

    // =========================================================
    //  PAGE 3 — Rythme cardiaque
    // =========================================================

    private JPanel creerPageRythme() {
        rythmeCardiaque = new RythmeCardiaque();

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(Color.BLACK);

        JPanel dessin = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                     RenderingHints.VALUE_ANTIALIAS_ON);
                rythmeCardiaque.drawPage(g2d, getWidth() / 2, getHeight() / 2);
            }
        };
        dessin.setBackground(Color.BLACK);
        dessin.setPreferredSize(new Dimension(350, 350));

        page.add(dessin, BorderLayout.CENTER);
        return page;
    }

    // =========================================================
    //  PAGE 4 — Batterie
    // =========================================================

    private JPanel creerPageBatterie() {
        etatBatterie = new EtatBatterie();

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(Color.BLACK);

        JPanel dessin = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                     RenderingHints.VALUE_ANTIALIAS_ON);
                etatBatterie.drawPage(g2d, getWidth() / 2, getHeight() / 2);
            }
        };
        dessin.setBackground(Color.BLACK);
        dessin.setPreferredSize(new Dimension(350, 350));

        page.add(dessin, BorderLayout.CENTER);
        return page;
    }

    // =========================================================
    //  PAGE 5 — Réglages
    // =========================================================

    private JPanel creerPageReglages() {
        JPanel page = new JPanel(new BorderLayout());
        page.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));

        // --- Forme du boîtier ---
        options.add(new JLabel("Forme du boîtier :"));
        JRadioButton rbRond   = new JRadioButton("Rond",  true);
        JRadioButton rbCarre  = new JRadioButton("Carré");
        ButtonGroup  grForme  = new ButtonGroup();
        grForme.add(rbRond);
        grForme.add(rbCarre);

        rbRond.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reglages.setFormeBoitier(ReglagesMontre.Forme.ROND);
                watchPanel.repaint();
            }
        });
        rbCarre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reglages.setFormeBoitier(ReglagesMontre.Forme.CARRE);
                watchPanel.repaint();
            }
        });

        options.add(rbRond);
        options.add(rbCarre);
        options.add(Box.createVerticalStrut(15));

        // Couleur & type police

        JButton btnCouleurAig= new JButton("Couleur aiguille");
        btnCouleurAig.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent e){
                 Color c = JColorChooser.showDialog(null, "Couleur des aiguilles", Color.RED);
                 if ( c != null){
                    reglages.setCouleurAiguilles(c);
                    for (ElementMontre el : modele.getElements()){
                        el.setCouleur(c);
                    }
                    watchPanel.repaint();
                 }
            }
        });
        String[] polices = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> comboPolice = new JComboBox<>(polices);
        comboPolice.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent e){
                String police = (String) comboPolice.getSelectedItem();
                if ( police != null){
                    for (ElementMontre el : modele.getElements()){
                        el.setPolice(police);
                    }
                    watchPanel.repaint();
                }
            }
        });

        JButton btnCouleurNombres = new JButton("Couleur des chiffres");
        btnCouleurNombres.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "Couleur des chiffres", reglages.getCouleurNombres());
                if (c != null) {
                    reglages.setCouleurNombres(c);
                    watchPanel.repaint();
                }
            }
        });

        JLabel lblEpaisseur = new JLabel("Épaisseur des aiguilles : 1");
        JSlider sliderEpaisseur = new JSlider(1, 5, 1);
        sliderEpaisseur.setMajorTickSpacing(1);
        sliderEpaisseur.setPaintTicks(true);
        sliderEpaisseur.setPaintLabels(true);
        sliderEpaisseur.setMaximumSize(new Dimension(200, 50));
        sliderEpaisseur.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                float facteur = sliderEpaisseur.getValue();
                lblEpaisseur.setText("Épaisseur des aiguilles : " + sliderEpaisseur.getValue());
                reglages.setFacteurEpaisseur(facteur);
                appliquerFacteurEpaisseur(facteur);
            }
        });

        options.add(new JLabel(" Couleur & type police :"));
        options.add(btnCouleurAig);
        options.add(btnCouleurNombres);
        options.add(Box.createVerticalStrut(10));
        options.add(lblEpaisseur);
        options.add(sliderEpaisseur);
        options.add(Box.createVerticalStrut(5));
        options.add(new JLabel("type de police :"));
        options.add(comboPolice);
        options.add(Box.createVerticalStrut(15));
        comboPolice.setMaximumSize(new Dimension(200, 30));



        // --- Couleur du fond ---
        options.add(new JLabel("Fond :"));
        
        JRadioButton rbUniforme = new JRadioButton("Uniforme", true);
        JRadioButton rbDegrade  = new JRadioButton("Dégradé");
        JRadioButton rbImage    = new JRadioButton("Image");
        ButtonGroup  grFond     = new ButtonGroup();
        grFond.add(rbUniforme);
        grFond.add(rbDegrade);
        grFond.add(rbImage);

        JButton btnChoisirImage = new JButton("Choisir une image...");
        btnChoisirImage.setEnabled(false);

        rbUniforme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reglages.setTypeFond(ReglagesMontre.TypeFond.UNIFORME);
                btnChoisirImage.setEnabled(false);
                watchPanel.repaint();
            }
        });
        rbDegrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reglages.setTypeFond(ReglagesMontre.TypeFond.DEGRADE);
                btnChoisirImage.setEnabled(false);
                watchPanel.repaint();
            }
        });
        
        JButton choixCouleur = new JButton("Couleur de fond");
        choixCouleur.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent e){
                Color c = JColorChooser.showDialog(null, "Couleur du fond", reglages.getCouleurFond());
                if (c != null) {
                    reglages.setCouleurFond(c);
                    watchPanel.repaint();
                }
            }
        });

        JButton choixCouleur2 = new JButton("Couleur de fond 2 (dégradé)");
        choixCouleur2.setEnabled(false);
        choixCouleur2.addActionListener(new ActionListener(){
            @Override public void actionPerformed(ActionEvent e){
                Color c = JColorChooser.showDialog(null, "Deuxième couleur du dégradé", reglages.getCouleurFond2());
                if (c != null) {
                    reglages.setCouleurFond2(c);
                    watchPanel.repaint();
                }
            }
        });

        rbUniforme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choixCouleur2.setEnabled(false);
            }
        });
        rbDegrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choixCouleur2.setEnabled(true);
            }
        });

        rbImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnChoisirImage.setEnabled(true);
                choixCouleur.setEnabled(false);
                choixCouleur2.setEnabled(false);
                if (reglages.getImageFond() != null) {
                    reglages.setTypeFond(ReglagesMontre.TypeFond.IMAGE);
                    watchPanel.repaint();
                }
            }
        });
        btnChoisirImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                        "Images (jpg, png, gif)", "jpg", "jpeg", "png", "gif"));
                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String chemin = fc.getSelectedFile().getAbsolutePath();
                    Image img = new javax.swing.ImageIcon(chemin).getImage();
                    reglages.setImageFond(img, chemin);
                    watchPanel.repaint();
                }
            }
        });

        options.add(rbUniforme);
        options.add(rbDegrade);
        options.add(choixCouleur);
        options.add(choixCouleur2);
        options.add(rbImage);
        options.add(btnChoisirImage);
   
        options.add(Box.createVerticalStrut(15));

        // --- Type d'affichage ---
        options.add(new JLabel("Affichage :"));
        JRadioButton rbAnalogique  = new JRadioButton("Analogique", true);
        JRadioButton rbNumerique   = new JRadioButton("Numérique");
        ButtonGroup  grAffichage   = new ButtonGroup();
        grAffichage.add(rbAnalogique);
        grAffichage.add(rbNumerique);

        rbAnalogique.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reglages.setTypeAffichage(ReglagesMontre.TypeAffichage.ANALOGIQUE);
                modele.retirerElementType(HorlogeNumerique.class);

                AiguilleHeures ah = new AiguilleHeures();
                AiguilleMinutes am = new AiguilleMinutes();
                AiguilleSecondes as = new AiguilleSecondes();

                ah.setCouleur(reglages.getCouleurAiguilles());
                am.setCouleur(reglages.getCouleurAiguilles());
                as.setCouleur(reglages.getCouleurAiguilles());
                ah.setFacteurEpaisseur(reglages.getFacteurEpaisseur());
                am.setFacteurEpaisseur(reglages.getFacteurEpaisseur());
                as.setFacteurEpaisseur(reglages.getFacteurEpaisseur());

                modele.ajouterElement(ah);
                modele.ajouterElement(am);
                modele.ajouterElement(as);
                watchPanel.repaint();
            }
        });     
        rbNumerique.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reglages.setTypeAffichage(ReglagesMontre.TypeAffichage.NUMERIQUE);
                modele.retirerElementType(AiguilleHeures.class);
                modele.retirerElementType(AiguilleMinutes.class);
                modele.retirerElementType(AiguilleSecondes.class);
                modele.retirerElementType(MiniCadranSecondes.class);
                modele.retirerElementType(CompteurSecondes.class);
                modele.ajouterElement(new HorlogeNumerique());
                watchPanel.repaint();
            }
        });
        JButton btnSauvegarder = new JButton("Sauvegarder");
        btnSauvegarder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        modele.sauvegarder(fc.getSelectedFile().getAbsolutePath());
                        JOptionPane.showMessageDialog(null, "Montre sauvegardée !");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
                    }
                }
            }
        });

        JButton btnCharger = new JButton("Charger");
        btnCharger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        modele = WatchModel.charger(fc.getSelectedFile().getAbsolutePath());
                        reglages = modele.getReglages();
                        // Recharge l'image depuis le chemin si elle n'a pas été restaurée (champ transient)
                        if (reglages.getTypeFond() == ReglagesMontre.TypeFond.IMAGE
                                && reglages.getImageFond() == null
                                && reglages.getCheminImageFond() != null) {
                            Image img = new javax.swing.ImageIcon(reglages.getCheminImageFond()).getImage();
                            reglages.setImageFond(img, reglages.getCheminImageFond());
                        }
                        watchPanel.setModele(modele);
                        watchPanel.setReglages(reglages);
                        watchPanel.repaint();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
                    }
                }
            }
        });



        options.add(rbAnalogique);
        options.add(rbNumerique);

        options.add(Box.createVerticalStrut(15));
        options.add(btnSauvegarder);
        options.add(btnCharger);

        page.add(options, BorderLayout.CENTER);
        return page;
    }

    // =========================================================
    //  Main
    // =========================================================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ReglagesMontre reglages = new ReglagesMontre();
                WatchModel modele = new WatchModel(reglages);

                modele.ajouterElement(new AiguilleHeures());
                modele.ajouterElement(new AiguilleMinutes());
                modele.ajouterElement(new AiguilleSecondes());

                new MainFrame(modele, reglages);
            }
        });
    }
}