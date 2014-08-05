using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using AMG.controller;
using AMG.model;

namespace AMG.view
{
    class TilePanel : Panel
    {
        private AMGForm gui;
        private int x;
        private int y;
        private bool draw = true;
        private Graphics g;

        public TilePanel(int x, int y, AMGForm f)
        {
            this.gui = f;
            this.x = x;
            this.y = y;
            InitializeComponent();
        }

        private void InitializeComponent()
        {
            this.SuspendLayout();
            // 
            // TilePanel
            // 
            this.Margin = new System.Windows.Forms.Padding(0);
            this.MaximumSize = new System.Drawing.Size(15, 15);
            this.MinimumSize = new System.Drawing.Size(15, 15);
            this.Size = new System.Drawing.Size(15, 15);
            this.Paint += new System.Windows.Forms.PaintEventHandler(this.PaintPanel);
            this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.TilePanel_MouseDown);
            this.MouseLeave += new System.EventHandler(this.TilePanel_MouseLeave);
            this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.TilePanel_MouseMove);
            this.ResumeLayout(false);

        }

        private void PaintPanel(object sender, PaintEventArgs e)
        {
            HeadController.TileType type = gui.Controller.Map.GetGrid()[x,y].Type;
            Color teamColor = gui.Controller.GetTeamColor(gui.Controller.Map.GetGrid()[x, y].Team);

            this.BackColor = gui.Controller.GetTileBackGroundColor(gui.Controller.Map.GetGrid()[x, y]);

            if (type == HeadController.TileType.Hill)
            {
                Pen p1 = new Pen(teamColor, 2);
                Rectangle rect1 = new Rectangle(2, 2, this.Width - 4, this.Height - 4);
                e.Graphics.DrawEllipse(p1, rect1);
            }

            if (type == HeadController.TileType.Ant)
            {
                SolidBrush myBrush = new SolidBrush(teamColor);
                Rectangle rect = new Rectangle(2, 2, this.Width - 4, this.Height - 4);
                e.Graphics.FillEllipse(myBrush, rect);
            }
            DrawGrid(e);
        }

        private void DrawGrid(PaintEventArgs e)
        {
            Pen p = new Pen(Color.Black, 1);
            e.Graphics.DrawLine(p, 0, 0, this.Width, 0);
            e.Graphics.DrawLine(p, this.Width, 0, this.Width, this.Height);
            e.Graphics.DrawLine(p, this.Width, this.Height, 0, this.Height);
            e.Graphics.DrawLine(p, 0, 0, 0, this.Height);
        }

        private void DrawSelection()
        {
            g = this.CreateGraphics();
            Pen p = new Pen(Color.White, 5);
            g.DrawLine(p, 0, 0, this.Width, 0);
            g.DrawLine(p, this.Width, 0, this.Width, this.Height);
            g.DrawLine(p, this.Width, this.Height, 0, this.Height);
            g.DrawLine(p, 0, 0, 0, this.Height);
        }

        private void TilePanel_MouseMove(object sender, MouseEventArgs e)
        {
            gui.CoLabel.Text = "X: " + y + "  Y: " + x;
            HeadController.TileType type = gui.GetTileType();
            Tile tile = gui.Controller.Map.GetGrid()[x, y];
            int team = gui.GetTeamNumber();
            bool antOnHill = gui.GetOnHill();

            if (e.Button == System.Windows.Forms.MouseButtons.Left)
            {
                Control control = (Control) sender;
                if (control.Capture)
                {
                    control.Capture = false;
                }
                if (control.ClientRectangle.Contains(e.Location))
                {
                    gui.Controller.SetTileType(type, tile, team, antOnHill);
                }
                if (draw)
                {
                    this.Refresh();
                }
            }
            DrawSelection();
            draw = false;

        }

        private void TilePanel_MouseLeave(object sender, EventArgs e)
        {
            if (g != null)
            {
                g.Dispose();
                this.Refresh();
                draw = true;
            }
        }

        private void TilePanel_MouseDown(object sender, MouseEventArgs e)
        {
            HeadController.TileType type = gui.GetTileType();
            Tile tile = gui.Controller.Map.GetGrid()[x, y];
            int team = gui.GetTeamNumber();
            bool antOnHill = gui.GetOnHill();

            Control control = (Control)sender;
            if (control.Capture)
            {
                control.Capture = false;
            }
            if (control.ClientRectangle.Contains(e.Location))
            {
                gui.Controller.SetTileType(type, tile, team, antOnHill);
            }
            this.Refresh();
            DrawSelection();
        }
    }
    }
