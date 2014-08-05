namespace AMG
{
    partial class AMGForm
    {
        /// <summary>
        /// Erforderliche Designervariable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Verwendete Ressourcen bereinigen.
        /// </summary>
        /// <param name="disposing">True, wenn verwaltete Ressourcen gelöscht werden sollen; andernfalls False.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Vom Windows Form-Designer generierter Code

        /// <summary>
        /// Erforderliche Methode für die Designerunterstützung.
        /// Der Inhalt der Methode darf nicht mit dem Code-Editor geändert werden.
        /// </summary>
        private void InitializeComponent()
        {
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.toolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.neueKarteToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.exitMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.karteToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.loadMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolPanel = new System.Windows.Forms.Panel();
            this.CoLabel = new System.Windows.Forms.Label();
            this.onHillComboBox = new System.Windows.Forms.ComboBox();
            this.onHillLabel = new System.Windows.Forms.Label();
            this.teamLabel = new System.Windows.Forms.Label();
            this.teamComboBox = new System.Windows.Forms.ComboBox();
            this.TileTypeComboBox = new System.Windows.Forms.ComboBox();
            this.BrushTypeComboBox = new System.Windows.Forms.ComboBox();
            this.BrushSizeComboBox = new System.Windows.Forms.ComboBox();
            this.tileTypeLabel = new System.Windows.Forms.Label();
            this.brushSizeLabel = new System.Windows.Forms.Label();
            this.brushTypeLabel = new System.Windows.Forms.Label();
            this.menuStrip1.SuspendLayout();
            this.toolPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuStrip1
            // 
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripMenuItem1,
            this.karteToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(784, 24);
            this.menuStrip1.TabIndex = 0;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // toolStripMenuItem1
            // 
            this.toolStripMenuItem1.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.neueKarteToolStripMenuItem,
            this.exitMenuItem});
            this.toolStripMenuItem1.Name = "toolStripMenuItem1";
            this.toolStripMenuItem1.Size = new System.Drawing.Size(46, 20);
            this.toolStripMenuItem1.Text = "Datei";
            // 
            // neueKarteToolStripMenuItem
            // 
            this.neueKarteToolStripMenuItem.Name = "neueKarteToolStripMenuItem";
            this.neueKarteToolStripMenuItem.Size = new System.Drawing.Size(152, 22);
            this.neueKarteToolStripMenuItem.Text = "Neue Karte";
            this.neueKarteToolStripMenuItem.Click += new System.EventHandler(this.neueKarteToolStripMenuItem_Click);
            // 
            // exitMenuItem
            // 
            this.exitMenuItem.Name = "exitMenuItem";
            this.exitMenuItem.Size = new System.Drawing.Size(152, 22);
            this.exitMenuItem.Text = "Beenden";
            this.exitMenuItem.Click += new System.EventHandler(this.exitMenuItem_Click);
            // 
            // karteToolStripMenuItem
            // 
            this.karteToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.loadMenuItem,
            this.saveMenuItem});
            this.karteToolStripMenuItem.Name = "karteToolStripMenuItem";
            this.karteToolStripMenuItem.Size = new System.Drawing.Size(46, 20);
            this.karteToolStripMenuItem.Text = "Karte";
            // 
            // loadMenuItem
            // 
            this.loadMenuItem.Name = "loadMenuItem";
            this.loadMenuItem.Size = new System.Drawing.Size(126, 22);
            this.loadMenuItem.Text = "Laden";
            this.loadMenuItem.Click += new System.EventHandler(this.loadMenuItem_Click);
            // 
            // saveMenuItem
            // 
            this.saveMenuItem.Name = "saveMenuItem";
            this.saveMenuItem.Size = new System.Drawing.Size(126, 22);
            this.saveMenuItem.Text = "Speichern";
            this.saveMenuItem.Click += new System.EventHandler(this.saveMenuItem_Click);
            // 
            // toolPanel
            // 
            this.toolPanel.Controls.Add(this.CoLabel);
            this.toolPanel.Controls.Add(this.onHillComboBox);
            this.toolPanel.Controls.Add(this.onHillLabel);
            this.toolPanel.Controls.Add(this.teamLabel);
            this.toolPanel.Controls.Add(this.teamComboBox);
            this.toolPanel.Controls.Add(this.TileTypeComboBox);
            this.toolPanel.Controls.Add(this.BrushTypeComboBox);
            this.toolPanel.Controls.Add(this.BrushSizeComboBox);
            this.toolPanel.Controls.Add(this.tileTypeLabel);
            this.toolPanel.Controls.Add(this.brushSizeLabel);
            this.toolPanel.Controls.Add(this.brushTypeLabel);
            this.toolPanel.Location = new System.Drawing.Point(12, 27);
            this.toolPanel.Name = "toolPanel";
            this.toolPanel.Size = new System.Drawing.Size(106, 354);
            this.toolPanel.TabIndex = 1;
            // 
            // CoLabel
            // 
            this.CoLabel.AutoSize = true;
            this.CoLabel.Location = new System.Drawing.Point(28, 316);
            this.CoLabel.Name = "CoLabel";
            this.CoLabel.Size = new System.Drawing.Size(30, 13);
            this.CoLabel.TabIndex = 10;
            this.CoLabel.Text = "X: Y:";
            // 
            // onHillComboBox
            // 
            this.onHillComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.onHillComboBox.FormattingEnabled = true;
            this.onHillComboBox.Items.AddRange(new object[] {
            "Ja",
            "Nein"});
            this.onHillComboBox.Location = new System.Drawing.Point(12, 262);
            this.onHillComboBox.Name = "onHillComboBox";
            this.onHillComboBox.Size = new System.Drawing.Size(74, 21);
            this.onHillComboBox.TabIndex = 9;
            // 
            // onHillLabel
            // 
            this.onHillLabel.AutoSize = true;
            this.onHillLabel.Location = new System.Drawing.Point(4, 243);
            this.onHillLabel.Name = "onHillLabel";
            this.onHillLabel.Size = new System.Drawing.Size(54, 13);
            this.onHillLabel.TabIndex = 8;
            this.onHillLabel.Text = "Auf Hügel";
            // 
            // teamLabel
            // 
            this.teamLabel.AutoSize = true;
            this.teamLabel.Location = new System.Drawing.Point(4, 190);
            this.teamLabel.Name = "teamLabel";
            this.teamLabel.Size = new System.Drawing.Size(34, 13);
            this.teamLabel.TabIndex = 7;
            this.teamLabel.Text = "Team";
            // 
            // teamComboBox
            // 
            this.teamComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.teamComboBox.FormattingEnabled = true;
            this.teamComboBox.Items.AddRange(new object[] {
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9"});
            this.teamComboBox.Location = new System.Drawing.Point(12, 210);
            this.teamComboBox.Name = "teamComboBox";
            this.teamComboBox.Size = new System.Drawing.Size(74, 21);
            this.teamComboBox.TabIndex = 6;
            // 
            // TileTypeComboBox
            // 
            this.TileTypeComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.TileTypeComboBox.FormattingEnabled = true;
            this.TileTypeComboBox.Items.AddRange(new object[] {
            "Wasser",
            "Hügel",
            "Land",
            "Ameise",
            "Futter"});
            this.TileTypeComboBox.Location = new System.Drawing.Point(12, 134);
            this.TileTypeComboBox.Name = "TileTypeComboBox";
            this.TileTypeComboBox.Size = new System.Drawing.Size(74, 21);
            this.TileTypeComboBox.TabIndex = 5;
            // 
            // BrushTypeComboBox
            // 
            this.BrushTypeComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.BrushTypeComboBox.Enabled = false;
            this.BrushTypeComboBox.FormattingEnabled = true;
            this.BrushTypeComboBox.Items.AddRange(new object[] {
            "1",
            "2",
            "3",
            "4",
            "5"});
            this.BrushTypeComboBox.Location = new System.Drawing.Point(12, 82);
            this.BrushTypeComboBox.Name = "BrushTypeComboBox";
            this.BrushTypeComboBox.Size = new System.Drawing.Size(74, 21);
            this.BrushTypeComboBox.TabIndex = 4;
            // 
            // BrushSizeComboBox
            // 
            this.BrushSizeComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.BrushSizeComboBox.Enabled = false;
            this.BrushSizeComboBox.FormattingEnabled = true;
            this.BrushSizeComboBox.Items.AddRange(new object[] {
            "Quadrat",
            "Kreis",
            "Linie"});
            this.BrushSizeComboBox.Location = new System.Drawing.Point(12, 28);
            this.BrushSizeComboBox.Name = "BrushSizeComboBox";
            this.BrushSizeComboBox.Size = new System.Drawing.Size(74, 21);
            this.BrushSizeComboBox.TabIndex = 3;
            // 
            // tileTypeLabel
            // 
            this.tileTypeLabel.AutoSize = true;
            this.tileTypeLabel.Location = new System.Drawing.Point(4, 115);
            this.tileTypeLabel.Name = "tileTypeLabel";
            this.tileTypeLabel.Size = new System.Drawing.Size(54, 13);
            this.tileTypeLabel.TabIndex = 2;
            this.tileTypeLabel.Text = "Kacheltyp";
            // 
            // brushSizeLabel
            // 
            this.brushSizeLabel.AutoSize = true;
            this.brushSizeLabel.Location = new System.Drawing.Point(4, 63);
            this.brushSizeLabel.Name = "brushSizeLabel";
            this.brushSizeLabel.Size = new System.Drawing.Size(62, 13);
            this.brushSizeLabel.TabIndex = 1;
            this.brushSizeLabel.Text = "Pinselgröße";
            // 
            // brushTypeLabel
            // 
            this.brushTypeLabel.AutoSize = true;
            this.brushTypeLabel.Location = new System.Drawing.Point(3, 9);
            this.brushTypeLabel.Name = "brushTypeLabel";
            this.brushTypeLabel.Size = new System.Drawing.Size(47, 13);
            this.brushTypeLabel.TabIndex = 0;
            this.brushTypeLabel.Text = "Pinselart";
            // 
            // AMGForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.ClientSize = new System.Drawing.Size(784, 395);
            this.Controls.Add(this.toolPanel);
            this.Controls.Add(this.menuStrip1);
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "AMGForm";
            this.Text = "Ant-Challange: Karteneditor";
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.toolPanel.ResumeLayout(false);
            this.toolPanel.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem toolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem neueKarteToolStripMenuItem;
        private System.Windows.Forms.Panel toolPanel;
        private System.Windows.Forms.ComboBox TileTypeComboBox;
        private System.Windows.Forms.ComboBox BrushTypeComboBox;
        private System.Windows.Forms.ComboBox BrushSizeComboBox;
        private System.Windows.Forms.Label tileTypeLabel;
        private System.Windows.Forms.Label brushSizeLabel;
        private System.Windows.Forms.Label brushTypeLabel;
        private System.Windows.Forms.ToolStripMenuItem karteToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem loadMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveMenuItem;
        private System.Windows.Forms.Label teamLabel;
        private System.Windows.Forms.ComboBox teamComboBox;
        private System.Windows.Forms.ComboBox onHillComboBox;
        private System.Windows.Forms.Label onHillLabel;
        public System.Windows.Forms.Label CoLabel;
        private System.Windows.Forms.ToolStripMenuItem exitMenuItem;
    }
}

