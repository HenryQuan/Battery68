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
        print(UIDevice.current.batteryLevel)
        self.BatteryLabel.text = "\(UIDevice.current.batteryLevel)"
    }

}

