using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using AMG.controller;

namespace AMG.model
{
    public class Grid
    {
        private Tile[,] map;

        public Grid(int row , int column)
        {
            Row = row;
            Column = column;
            map = new Tile[row,column];
            for (int x = 0; x < row; x++)
            {
                for (int y = 0; y < column; y++)
                {
                    map[x,y] = new Tile(x,y,HeadController.TileType.Land);
                }
            }
        }

        public decimal Row { get; private set; }

        public decimal Column { get; private set; }

        public Tile[,] GetGrid()
        {
            return map;
        }
    }
}
