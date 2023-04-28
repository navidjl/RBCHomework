package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.TransactionAdapter
import com.example.myapplication.databinding.FragmentAccountTransactionBinding
import com.example.myapplication.state.AccountTransactionState
import com.example.myapplication.viewmodel.AccountTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountTransactionFragment : Fragment() {

    private lateinit var binding: FragmentAccountTransactionBinding
    private val accountTransactionViewModel: AccountTransactionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountTransactionBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = accountTransactionViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountTransactionViewModel.account =
            AccountTransactionFragmentArgs.fromBundle(requireArguments()).AccountInformation
        accountTransactionViewModel.getTransactionsForAccount()

        accountTransactionViewModel.stateLd.observe(viewLifecycleOwner) { state ->
            when (state) {
                AccountTransactionState.Error -> {
                    binding.loadingView.root.visibility = View.GONE
                    binding.headerStatus.text = getString(R.string.account_transaction_error)
                }
                AccountTransactionState.Loading -> {
                    binding.headerStatus.text = getString(R.string.account_transaction_loading)
                }
                is AccountTransactionState.Success -> {
                    binding.loadingView.root.visibility = View.GONE
                    with(binding) {
                        accountInfoContainer.visibility = View.VISIBLE
                        headerStatus.text =
                            getString(R.string.account_transaction_transactions_title)
                        transactionListRecyclerView.adapter =
                            TransactionAdapter(state.transactions)
                        transactionListRecyclerView.layoutManager =
                            LinearLayoutManager(requireContext())
                        transactionListRecyclerView.setHasFixedSize(true)
                        transactionListRecyclerView.visibility = View.VISIBLE

                    }
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this.viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            })

    }
}
