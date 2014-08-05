
namespace NFTC.Observer
{
    public interface IObserver
    {
        /// <summary>Dient dazu die grafische Oberfläche zu aktuallsieren. Wird von jeder grafischen Oberfäche
        /// überschrieben.</summary>
        /// <param>Keine Parameter vorhanden</param>
        /// <returns>Kein Rückgabewert vorhanden.</returns>
        void ChangeThingsUp();
    }
}
