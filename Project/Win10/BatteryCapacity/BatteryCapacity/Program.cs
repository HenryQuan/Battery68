using System;
using System.Diagnostics;
using System.IO;
using System.Text.RegularExpressions;

namespace BatteryCapacity
{
    class Program
    {
        const string BATTERY_REPORT = "battery-report.html";
        // A template for regex to get data from html
        const string REGEX_TEMPLATE = "{0}</span></td><td>(.*)";

        static void Main(string[] args)
        {
            // This might fail due to ???
            if (GenBatteryReport())
            {
                // Parse the html to calculate current battery capacity
                var percentage = CalcBatteryCapacity();
                Console.WriteLine($"\nESTIMATED -> {percentage}%\n");
                // Ask user whether he/she wants to see the complete report
                Console.Write("Do you want to see report [y/n]: ");
                string choice = Console.ReadLine().ToLower();
                if (choice == "y" || choice == "yes")
                {
                    Process.Start(BATTERY_REPORT);
                }
            }

            // Remove report
            File.Delete(BATTERY_REPORT);
        }

        /// <summary>
        /// Get designed capacity and current capacity
        /// use simple division to get a estimate
        /// </summary>
        /// <returns></returns>
        static double CalcBatteryCapacity()
        {
            // Read all text from html
            var html = File.ReadAllText(BATTERY_REPORT);
            // Get the information we want
            var design = GetRegexMatch("DESIGN CAPACITY", html);
            var curr = GetRegexMatch("FULL CHARGE CAPACITY", html);
            GetRegexMatch("CYCLE COUNT", html);

            // Round to only 2 digits
            return Math.Round(Normalise(curr) / Normalise(design) * 100, 2);
        }

        /// <summary>
        /// Print the information and return the value
        /// </summary>
        /// <param name="input"></param>
        /// <param name="html"></param>
        /// <returns></returns>
        static string GetRegexMatch(string input, string html)
        {
            var regex = new Regex(String.Format(REGEX_TEMPLATE, input));
            var match = regex.Match(html);
            if (match.Length > 1)
            {
                // 1 is the macth
                var value = match.Groups[1].ToString();
                Console.WriteLine($"{input}: {value}");
                return value;
            }
            else
            {
                return "Unkown";
            }
        }

        /// <summary>
        /// Convert 63,123 mah to 63123.0
        /// </summary>
        /// <param name="capacity"></param>
        /// <returns></returns>
        static double Normalise(string capacity)
        {
            var noMWh = capacity.Replace(" mWh", "");
            var noComma = noMWh.Replace(",", "");
            return Convert.ToDouble(noComma);
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
                Console.WriteLine("Completed\n");
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
