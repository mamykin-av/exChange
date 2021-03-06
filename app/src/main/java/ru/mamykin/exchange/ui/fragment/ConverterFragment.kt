package ru.mamykin.exchange.ui.fragment

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_converter.*
import ru.mamykin.exchange.R
import ru.mamykin.exchange.core.di.Scopes
import ru.mamykin.exchange.core.platform.BaseFragment
import ru.mamykin.exchange.domain.entity.RateList
import ru.mamykin.exchange.presentation.presenter.ConverterPresenter
import ru.mamykin.exchange.presentation.view.ConverterView
import ru.mamykin.exchange.ui.adapter.CurrencyRatesRecyclerAdapter
import toothpick.Toothpick

/**
 * Converter screen
 */
class ConverterFragment : BaseFragment(), ConverterView {

    companion object {
        fun newInstance() = ConverterFragment()
    }

    override val layoutId = R.layout.fragment_converter

    @InjectPresenter
    lateinit var presenter: ConverterPresenter

    private lateinit var adapter: CurrencyRatesRecyclerAdapter

    @ProvidePresenter
    fun provideConverterPresenter(): ConverterPresenter {
        return Toothpick.openScopes(Scopes.APP_SCOPE, this)
                .getInstance(ConverterPresenter::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(presenter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRatesAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toothpick.closeScope(this)
    }

    override fun showLoading(show: Boolean) {
        loadingProgressBar.visibility = if (show) View.VISIBLE else View.GONE
        ratesRecyclerView.visibility = if (show) View.GONE else View.VISIBLE
    }

    override fun showRateList(rateList: RateList) {
        adapter.changeCurrencyRates(rateList.rates)
    }

    private fun initRatesAdapter() {
        adapter = CurrencyRatesRecyclerAdapter(context!!) { code, amount ->
            ratesRecyclerView.scrollToPosition(0)
            presenter.onCurrencyOrAmountChanged(code, amount)
        }
        adapter.setHasStableIds(true)
        ratesRecyclerView.setHasFixedSize(true)
        ratesRecyclerView.adapter = adapter
    }
}