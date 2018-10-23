using System.Diagnostics;

namespace BatteryCapacity
{
    class Program
    {
        static void Main(string[] args)
        {
            var battery = new Process();
            var startInfo = new ProcessStartInfo();
            // This is to hide the cmd
            startInfo.WindowStyle = ProcessWindowStyle.Hidden;
            startInfo.FileName = "CMD.exe";
            // Generate battery report html, /C is compulsory (argument wont be passed without it)
            startInfo.Arguments = "/C powercfg /batteryreport";
            battery.StartInfo = startInfo;
            battery.Start();
        }
    }
}
