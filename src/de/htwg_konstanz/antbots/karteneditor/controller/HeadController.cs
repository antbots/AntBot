using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net.Mail;
using System.Net.Mime;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using AMG.model;
using NFTC.Observer;

namespace AMG.controller
{

    public class HeadController : Observable
    {
        public Grid Map { get; private set; }

        public enum TileType
        {
            Water,
            Land,
            Hill,
            Ant,
            Food
        }

        public HeadController()
        {
        }

        public void CreateNewMap(int x, int y)
        {
            Map = new Grid(x, y);
        }

        public void SaveMap(FileStream fs)
        {
            if (fs != null)
            {
                StreamWriter templateWriter = new StreamWriter(fs);
                LinkedList<Int32> l = new LinkedList<int>();
                LinkedList<String> l1 = new LinkedList<String>();
                l1.AddLast("A");
                l1.AddLast("B");
                l1.AddLast("C");
                l1.AddLast("D");
                l1.AddLast("E");
                l1.AddLast("F");
                l1.AddLast("G");
                l1.AddLast("H");
                l1.AddLast("I");
                l1.AddLast("J");
                int count = 0;
                for (int x = 0; x < Map.Row; x++)
                {
                    for (int y = 0; y < Map.Column; y++)
                    {
                        String s = Map.GetGrid()[x, y].ToString();
                        int i = 0;
                        bool result = int.TryParse(s, out i);
                        if (result && !l.Contains(i))
                        {
                            l.AddFirst(i);
                        }
                        if (l1.Contains(s))
                        {
                            count++;
                        }
                    }
                }
                templateWriter.Write("players " + (l.Count + count) + Environment.NewLine);
                templateWriter.Write("rows " + Map.Row + Environment.NewLine);
                templateWriter.Write("cols " + Map.Column + Environment.NewLine);
                
                for (int x = 0; x < Map.Row; x++)
                {
                    templateWriter.Write("m ");
                    for (int y = 0; y < Map.Column; y++)
                    {
                        templateWriter.Write(Map.GetGrid()[x,y].ToString());
                    }
                    templateWriter.Write(Environment.NewLine);
                }
                templateWriter.Dispose();
            }
        }

        public void LoadMap(Stream myStream)
        {
            if (myStream != null)
            {
                StreamReader fileReader = new StreamReader(myStream);
                int rows = 0;
                int columns = 0;
                LinkedList<String> l = new LinkedList<String>();
                l.AddLast("a");
                l.AddLast("b");
                l.AddLast("c");
                l.AddLast("d");
                l.AddLast("e");
                l.AddLast("f");
                l.AddLast("g");
                l.AddLast("h");
                l.AddLast("i");
                l.AddLast("j");
                l.AddLast("A");
                l.AddLast("B");
                l.AddLast("C");
                l.AddLast("D");
                l.AddLast("E");
                l.AddLast("F");
                l.AddLast("G");
                l.AddLast("H");
                l.AddLast("I");
                l.AddLast("J");

                int x = 0;
                while (!fileReader.EndOfStream)
                {
                    int result;
                    String s = fileReader.ReadLine();
                    if (s.Contains("players"))
                    {
                        continue;
                    }
                    if (s.Contains("rows"))
                    {
                        String rowLine = s.Substring(5);
                        rows = Int32.Parse(rowLine);
                        continue;
                    }
                    if (s.Contains("cols"))
                    {
                        String columnLine = s.Substring(5);
                        columns = Int32.Parse(columnLine);
                        CreateNewMap(rows,columns);
                        Notify();
                        continue;
                    }
                    String mapLine = s.Substring(2);
                    for (int y = 0; y < columns; y++)
                    {
                        if (mapLine[y] == '%')
                        {
                            System.Console.WriteLine(mapLine[y]);
                            Map.GetGrid()[x, y].SetTypeToWater();
                        }
                        if (mapLine[y] == '.')
                        {
                            Map.GetGrid()[x, y].SetTypeToLand();
                        }
                        if (Int32.TryParse(mapLine[y].ToString(), out result))
                        {
                            Map.GetGrid()[x, y].SetTypeToHill(result);
                        }
                        if (l.Contains(mapLine[y].ToString()))
                        {
                            int index = l.Select((item, inx) => new { item, inx }).First(v => v.item == mapLine[y].ToString()).inx;

                            for (int i = 0; i < 20; i++)
                            {
                                if (i%10 == index)
                                {
                                    if (i < 10)
                                    {
                                        Map.GetGrid()[x, y].SetTypeToAnt(index, false);
                                    }
                                    else
                                    {
                                        Map.GetGrid()[x, y].SetTypeToAnt(index, true);
                                    }
                                }
                            }
                        }
                        if (mapLine[y] == '*')
                        {
                            Map.GetGrid()[x, y].SetTypeToFood();
                        }
                    }
                    x++;
                }
                fileReader.Dispose();
            }
        }

        public Color GetTeamColor(int team)
        {
            switch (team)
            {
                case 0:
                    return Color.Red;
                    break;
                case 1:
                    return Color.MediumSeaGreen;
                    break;
                case 2:
                    return Color.CornflowerBlue;
                    break;
                case 3:
                    return Color.Orange;
                    break;
                case 4:
                    return Color.DarkViolet;
                    break;
                case 5:
                    return Color.Yellow;
                    break;
                case 6:
                    return Color.LightGreen;
                    break;
                case 7:
                    return Color.LightSkyBlue;
                    break;
                case 8:
                    return Color.LightCoral;
                    break;
                case 9:
                    return Color.Teal;
                    break;
                default:
                    return Color.LightGreen;
            }
        }

        public Color GetTileBackGroundColor(Tile tile)
        {
            TileType type = tile.Type;
            bool antOnHill = tile.AntOnHill;

            switch (type)
            {
                case HeadController.TileType.Hill:
                    return System.Drawing.Color.Sienna;
                    break;
                case HeadController.TileType.Land:
                    return System.Drawing.Color.Peru;
                    break;
                case HeadController.TileType.Water:
                    return System.Drawing.Color.RoyalBlue;
                    break;
                case HeadController.TileType.Food:
                    return System.Drawing.Color.Wheat;
                    break;
                case HeadController.TileType.Ant:
                    if (antOnHill)
                    {
                        return System.Drawing.Color.Sienna;
                    }
                    else
                    {
                        return System.Drawing.Color.Peru;
                    }
                default:
                    return System.Drawing.Color.Peru;
            }
        }

        public void SetTileType(TileType type, Tile tile, int team, bool antOnHill)
        {
            if (type == HeadController.TileType.Water)
            {
                tile.SetTypeToWater();
            }
            if (type == HeadController.TileType.Land)
            {
                tile.SetTypeToLand();
            }
            if (type == HeadController.TileType.Hill)
            {
                tile.SetTypeToHill(team);
            }
            if (type == HeadController.TileType.Ant)
            {
                tile.SetTypeToAnt(team, antOnHill);
            }
            if (type == HeadController.TileType.Food)
            {
                tile.SetTypeToFood();
            }
        }

        public TileType ParseTileTypeInput(string selectedItem)
        {
            switch (selectedItem)
            {
                case "Wasser":
                    return TileType.Water;
                case "Hügel":
                    return TileType.Hill;
                case "Land":
                    return TileType.Land;
                case "Ameise":
                    return TileType.Ant;
                case "Futter":
                    return TileType.Food;
                default:
                    return TileType.Land;
            }
        }

        public string ParseBrushTypeInput(string selectedItem)
        {
            switch (selectedItem)
            {
                case "Kreis":
                    return "c";
                case "Quadrat":
                    return "q";
                case "Linie":
                    return "l";
                default:
                    return "q";
            }
        }

        public bool ParseAntOnHillInput(string selectedItem)
        {
            switch (selectedItem)
            {
                case "Ja":
                    return true;
                case "Nein":
                    return false;
                default:
                    return false;
            }
        }

        public void ExitApplication()
        {
            Application.Exit();
        }
    }
}
