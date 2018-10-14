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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let battery = Int(UIDevice.current.batteryLevel * 100)
        self.BatteryLabel.text = "\(battery)%"
    }

}

