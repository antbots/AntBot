using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Windows.Forms;
using AMG.controller;

namespace AMG
{
    static class AMG
    {
        /// <summary>
        /// Der Haupteinstiegspunkt für die Anwendung.
        /// </summary>
        [STAThread]
        static void Main()
        {
            HeadController controller = new HeadController();
            AMGForm gui = new AMGForm(controller);
            controller.AddObserver(gui);
            Application.EnableVisualStyles();
            gui.Show();
            gui.FormClosed += CheckApplicationEnd;
            Application.Run();
        }

        private static void CheckApplicationEnd(object sender, FormClosedEventArgs e)
        {
            if (Application.OpenForms.Count == 0)
            {
                Application.Exit();
            }
        }
    }
}
