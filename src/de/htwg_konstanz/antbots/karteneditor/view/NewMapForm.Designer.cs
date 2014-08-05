namespace AMG.view
{
    partial class NewMapForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.rowTextBox = new System.Windows.Forms.TextBox();
            this.columnTextBox = new System.Windows.Forms.TextBox();
            this.row = new System.Windows.Forms.Label();
            this.column = new System.Windows.Forms.Label();
            this.okButton = new System.Windows.Forms.Button();
            this.title = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // rowTextBox
            // 
            this.rowTextBox.Location = new System.Drawing.Point(129, 52);
            this.rowTextBox.Name = "rowTextBox";
            this.rowTextBox.Size = new System.Drawing.Size(100, 20);
            this.rowTextBox.TabIndex = 0;
            this.rowTextBox.Text = "5";
            // 
            // columnTextBox
            // 
            this.columnTextBox.Location = new System.Drawing.Point(129, 79);
            this.columnTextBox.Name = "columnTextBox";
            this.columnTextBox.Size = new System.Drawing.Size(100, 20);
            this.columnTextBox.TabIndex = 1;
            this.columnTextBox.Text = "5";
            // 
            // row
            // 
            this.row.AutoSize = true;
            this.row.Location = new System.Drawing.Point(30, 55);
            this.row.Name = "row";
            this.row.Size = new System.Drawing.Size(41, 13);
            this.row.TabIndex = 2;
            this.row.Text = "Reihen";
            // 
            // column
            // 
            this.column.AutoSize = true;
            this.column.Location = new System.Drawing.Point(30, 82);
            this.column.Name = "column";
            this.column.Size = new System.Drawing.Size(43, 13);
            this.column.TabIndex = 3;
            this.column.Text = "Spalten";
            // 
            // okButton
            // 
            this.okButton.Location = new System.Drawing.Point(141, 116);
            this.okButton.Name = "okButton";
            this.okButton.Size = new System.Drawing.Size(75, 23);
            this.okButton.TabIndex = 4;
            this.okButton.Text = "ok";
            this.okButton.UseVisualStyleBackColor = true;
            this.okButton.Click += new System.EventHandler(this.okButton_Click);
            // 
            // title
            // 
            this.title.AutoSize = true;
            this.title.Location = new System.Drawing.Point(102, 20);
            this.title.Name = "title";
            this.title.Size = new System.Drawing.Size(106, 13);
            this.title.TabIndex = 5;
            this.title.Text = "Neue Karte erstellen:";
            // 
            // NewMapForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(303, 151);
            this.Controls.Add(this.title);
            this.Controls.Add(this.okButton);
            this.Controls.Add(this.column);
            this.Controls.Add(this.row);
            this.Controls.Add(this.columnTextBox);
            this.Controls.Add(this.rowTextBox);
            this.Name = "NewMapForm";
            this.Text = "Neue Karte erstellen";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox rowTextBox;
        private System.Windows.Forms.TextBox columnTextBox;
        private System.Windows.Forms.Label row;
        private System.Windows.Forms.Label column;
        private System.Windows.Forms.Button okButton;
        private System.Windows.Forms.Label title;
    }
}