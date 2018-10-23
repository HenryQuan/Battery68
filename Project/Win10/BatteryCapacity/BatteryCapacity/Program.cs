using System;
using System.Diagnostics;
using System.IO;

namespace BatteryCapacity
{
    class Program
    {
        const string BATTERY_REPORT = "battery-report.html";

        static void Main(string[] args)
        {
            // This might fail due to ???
            if (GenBatteryReport())
            {
                // Parse the html to calculate current battery capacity

                // Ask user whether he/she wants to see the complete report

                // Remove report
                File.Delete(BATTERY_REPORT);
            }

            Console.ReadKey();
        }

        /// <summary>
        /// Generate system battery report and wait for it to complete
        /// </summary>
        static bool GenBatteryReport()
        {
            Console.WriteLine("Generating battery report...");
            var battery = new Process();
            var startInfo = new ProcessStartInfo();
            // This is to hide the cmd
            startInfo.WindowStyle = ProcessWindowStyle.Hidden;
            startInfo.FileName = "CMD.exe";
            // Generate battery report html, /C is compulsory (argument wont be passed without it)
            startInfo.Arguments = "/C powercfg /batteryreport";
            battery.StartInfo = startInfo;
            battery.Start();
            // Wait for the report to be generated
            battery.WaitForExit();

            if (File.Exists(BATTERY_REPORT))
            {
                Console.WriteLine("Completed");
                return true;
            }
            else
            {
                Console.WriteLine("Failed to generate report. This is for Windows 8+");
                return false;
            }   
        }
    }
}
