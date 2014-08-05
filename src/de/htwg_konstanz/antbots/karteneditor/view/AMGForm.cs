using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Dynamic;
using System.IO;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using AMG.controller;
using AMG.view;
using NFTC.Observer;

namespace AMG
{
    public partial class AMGForm : Form, IObserver
    {
        public HeadController Controller { get; private set; }
        private TableLayoutPanel mapPanel { get; set; }

        public AMGForm(HeadController controller)
        {
            InitializeComponent();
            this.Controller = controller;
            BrushTypeComboBox.SelectedIndex = 0;
            TileTypeComboBox.SelectedIndex = 0;
            BrushSizeComboBox.SelectedIndex = 0;
            teamComboBox.SelectedIndex = 0;
            onHillComboBox.SelectedIndex = 0;
        }

        private void neueKarteToolStripMenuItem_Click(object sender, EventArgs e)
        {
            NewMapForm newMapForm = new NewMapForm(this);
            newMapForm.StartPosition = FormStartPosition.CenterParent;
            newMapForm.ShowDialog(this);
        }

        public HeadController.TileType GetTileType()
        {
            String selectedItem = TileTypeComboBox.SelectedItem.ToString();
            return Controller.ParseTileTypeInput(selectedItem);
        }

        public String GetBrushType()
        {
            String selectedItem = BrushTypeComboBox.SelectedItem.ToString();
            return Controller.ParseBrushTypeInput(selectedItem);
        }

        public bool GetOnHill()
        {
            String selectedItem = onHillComboBox.SelectedItem.ToString();
            return Controller.ParseAntOnHillInput(selectedItem);
        }

        public int GetBrushSize()
        {
            return Int32.Parse(BrushSizeComboBox.SelectedItem.ToString());
        }

        public int GetTeamNumber()
        {
            return Int32.Parse(teamComboBox.SelectedItem.ToString());
        }

        private void saveMenuItem_Click(object sender, EventArgs e)
        {
            SaveFileDialog saveFileDialog = new SaveFileDialog();
            saveFileDialog.OverwritePrompt = true;
            saveFileDialog.RestoreDirectory = true;
            saveFileDialog.InitialDirectory = "c:\\";
            saveFileDialog.Filter = "map files (*.map)|*.map";
            saveFileDialog.ShowDialog();

            if (saveFileDialog.FileName != "")
            {
                FileStream fs = (FileStream)saveFileDialog.OpenFile();
                Controller.SaveMap(fs);
                fs.Close();
            }
        }

        private void loadMenuItem_Click(object sender, EventArgs e)
        {
            OpenFileDialog openFileDialog1 = new OpenFileDialog();

            openFileDialog1.CheckFileExists = true;
            openFileDialog1.Multiselect = false;
            openFileDialog1.InitialDirectory = "c:\\";
            openFileDialog1.Filter = "map files (*.map)|*.map";
            openFileDialog1.RestoreDirectory = true;

            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                Stream myStream = openFileDialog1.OpenFile();
                Controller.LoadMap(myStream);
            }
        }

        public void CreateNewMap(int row, int column)
        {
            if (Controls.Contains(mapPanel))
            {
                Controls.Remove(mapPanel);
                mapPanel.Dispose();
            }

            Controller.CreateNewMap(row, column);

            mapPanel = new TableLayoutPanel();
            mapPanel.AutoSize = true;
            mapPanel.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            mapPanel.MaximumSize = new Size(3000, 800);
            mapPanel.AutoScroll = true;

            this.mapPanel.ColumnCount = column;
            /*for (int i = 0; i < column; i++)
            {
                //mapPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 88F));
            }*/
            this.mapPanel.Location = new System.Drawing.Point(124, 27);
            this.mapPanel.Name = "mapPanel";
            this.mapPanel.RowCount = row;
            /*for (int i = 0; i < row; i++)
            {
                //mapPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
            }*/
            this.mapPanel.Size = new System.Drawing.Size(485, 393);
            this.mapPanel.TabIndex = 2;

            for (int x = 0; x < row; x++)
            {
                for (int y = 0; y < column; y++)
                {
                    TilePanel t = new TilePanel(x, y, this);
                    t.Margin = new Padding(0);
                    mapPanel.Controls.Add(t);
                }
            }

            Controls.Add(mapPanel);
            Refresh();
        }

        public void ChangeThingsUp()
        {
            CreateNewMap((int)Controller.Map.Row, (int)Controller.Map.Column);
        }

        private void exitMenuItem_Click(object sender, EventArgs e)
        {
            Controller.ExitApplication();
        }
    }
}
