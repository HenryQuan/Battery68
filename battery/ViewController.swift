//
//  ViewController.swift
//  battery
//
//  Created by Yiheng Quan on 14/10/18.
//  Copyright © 2018 Yiheng Quan. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var BatteryLabel: UILabel!
    
    // Get current battery level
    var battery: Int {
        return Int(UIDevice.current.batteryLevel * 100)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.BatteryLabel.text = "\(battery)%"
        
        // Add observer to update percentage with updated
        NotificationCenter.default.addObserver(self, selector: #selector(batteryLevelDidChange), name: UIDevice.batteryLevelDidChangeNotification, object: nil)
    }
    
    @objc func batteryLevelDidChange(_ notification: Notification) {
        self.BatteryLabel.text = "\(battery)%"
    }
    
}

