using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;
using System.Threading.Tasks;
using AMG.controller;

namespace AMG.model
{
    public class Tile
    {
        public Tile(int x, int y, HeadController.TileType type)
        {
            X = x;
            Y = y;
            this.Type = type;
            this.Team = -1;
        }


        public override string ToString()
        {
            switch (Type)
            {
                case HeadController.TileType.Hill:
                    return Team.ToString();
                    break;
                case HeadController.TileType.Land:
                    return ".";
                    break;
                case HeadController.TileType.Water:
                    return "%";
                    break;
                case HeadController.TileType.Food:
                    return "*";
                    break;
                case HeadController.TileType.Ant:
                    String ant = "a";
                    switch (Team)
                    {
                        case 0:
                            ant = "a";
                            break;
                        case 1:
                            ant = "b";
                            break;
                        case 2:
                            ant = "c";
                            break;
                        case 3:
                            ant = "d";
                            break;
                        case 4:
                            ant = "e";
                            break;
                        case 5:
                            ant = "f";
                            break;
                        case 6:
                            ant = "g";
                            break;
                        case 7:
                            ant = "h";
                            break;
                        case 8:
                            ant = "i";
                            break;
                        case 9:
                            ant = "j";
                            break;
                    }
                    if (AntOnHill)
                    {
                        ant = ant.ToUpper();
                    }
                    return ant;
                    break;
                default:
                    return ".";
            }
        }

        public void SetTypeToHill(int team)
        {
            Type = HeadController.TileType.Hill;
            Team = team;
        }

        public void SetTypeToAnt(int team, bool onHill)
        {
            Type = HeadController.TileType.Ant;
            AntOnHill = onHill;
            Team = team;
        }

        public void SetTypeToLand()
        {
            Type = HeadController.TileType.Land;
        }

        public void SetTypeToWater()
        {
            Type = HeadController.TileType.Water;
        }

        public void SetTypeToFood()
        {
            Type = HeadController.TileType.Food;
        }

        public int Team { get; private set; }
        public bool AntOnHill { get; private set; }
        public int X { get; set; }
        public int Y { get; set; }
        public HeadController.TileType Type { get; private set; }
    }
}
