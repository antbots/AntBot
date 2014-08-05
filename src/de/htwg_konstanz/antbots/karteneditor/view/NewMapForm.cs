using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using AMG.controller;

namespace AMG.view
{
    public partial class NewMapForm : Form
    {
        private readonly AMGForm _amgForm;

        public NewMapForm(AMGForm amgForm)
        {
            _amgForm = amgForm;
            InitializeComponent();
        }

        private void okButton_Click(object sender, EventArgs e)
        {
            int row = Int32.Parse(rowTextBox.Text);
            int column = Int32.Parse(columnTextBox.Text);
            _amgForm.CreateNewMap(row,column);

            Close();
        }
    }
}
