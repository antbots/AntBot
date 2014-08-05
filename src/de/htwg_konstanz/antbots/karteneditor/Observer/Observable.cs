using System.Collections.Generic;

namespace NFTC.Observer
{
    public class Observable
    {
        /// <summary>Repräsentiert die Liste aller Observer</summary>
        protected LinkedList<IObserver> Subscibers = new LinkedList<IObserver>();

        /// <summary>Dient dazu eine grafische Oberfläche in die Observerliste aufzunehmen.</summary>
        /// <param name="s">Repräsentiert das Objekt das in die Observerliste aufgenommen werden soll</param>
        /// <returns>Kein Rückgabewert vorhanden.</returns>
        public void AddObserver(IObserver s)
        {
            Subscibers.AddFirst(s);
        }

        /// <summary>Dient dazu eine grafische Oberfläche aus der Observerliste zu entfernen.</summary>
        /// <param name="s">Repräsentiert das Objekt das aus der Observerliste entfernt werden soll</param>
        /// <returns>Kein Rückgabewert vorhanden.</returns>
        public void RemoveObserver(IObserver s)
        {
            Subscibers.Remove(s);
        }

        /// <summary>Dient dazu alle Observer in der Liste auf eine Änderung zu informieren.</summary>
        /// <param>Keine Parameter vorhanden</param>
        /// <returns>Kein Rückgabewert vorhanden.</returns>
        public void Notify()
        {
            foreach (IObserver observer in Subscibers)
            {
                observer.ChangeThingsUp();
            }
        }
    }
}
