package ru.mamykin.exchange.presentation.alerts

import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.platform.BaseFragment

class AlertsFragment : BaseFragment() {

    companion object {
        fun newInstance() = AlertsFragment()
    }

    override val layoutId = R.layout.fragment_alerts
}