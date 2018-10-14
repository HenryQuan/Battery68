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
    
    // Get colour according to current percentage
    var background: UIColor {
        // Material green
        if (battery > 80) { return UIColorFromRGB(rgb: 0x8BC34A) }
        // Material orange
        if (battery > 20) { return UIColorFromRGB(rgb: 0xFF9800) }
        // Material red
        return UIColorFromRGB(rgb: 0xF44336)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.BatteryLabel.text = "\(battery)%"
        self.view.backgroundColor = background
        
        // Add observer to update percentage with updated
        NotificationCenter.default.addObserver(self, selector: #selector(batteryLevelDidChange), name: UIDevice.batteryLevelDidChangeNotification, object: nil)
    }
    
    @objc func batteryLevelDidChange(_ notification: Notification) {
        self.BatteryLabel.text = "\(battery)%"
        self.view.backgroundColor = background
    }
    
    // From: https://stackoverflow.com/questions/24074257/how-can-i-use-uicolorfromrgb-in-swift
    func UIColorFromRGB(rgb: UInt) -> UIColor {
        return UIColor(
            red: CGFloat((rgb & 0xFF0000) >> 16) / 255.0,
            green: CGFloat((rgb & 0x00FF00) >> 8) / 255.0,
            blue: CGFloat(rgb & 0x0000FF) / 255.0,
            alpha: CGFloat(1.0)
        )
    }

    
}

