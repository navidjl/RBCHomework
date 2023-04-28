package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.AccountAdapter
import com.example.myapplication.databinding.FragmentAccountsListBinding
import com.example.myapplication.model.AccountDataRowItem
import com.example.myapplication.model.AccountInformation
import com.example.myapplication.state.AccountListState
import com.example.myapplication.util.OnItemClickListener
import com.example.myapplication.viewmodel.AccountsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountsListFragment : Fragment() {

    private lateinit var binding: FragmentAccountsListBinding
    private val accountsViewModel: AccountsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountsListBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = accountsViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountsViewModel.getAccounts()
        accountsViewModel.accountListState.observe(viewLifecycleOwner) { accountListState ->

            when (accountListState) {
                AccountListState.Error -> {
                    binding.accountsListRecyclerView.visibility = View.GONE
                }
                AccountListState.Loading -> {
                    binding.accountsListRecyclerView.visibility = View.GONE
                }
                is AccountListState.Success -> {
                    binding.accountsListRecyclerView.adapter =
                        AccountAdapter(
                            accountListState.accountTransactionRowItems,
                            object : OnItemClickListener {
                                override fun onItemClick(accountInformationRowItem: AccountDataRowItem) {
                                    val action =
                                        AccountsListFragmentDirections.actionAccountsListFragmentToAccountTransactionFragment(
                                            AccountInformation(accountInformationRowItem.account)
                                        )
                                    findNavController().navigate(action)
                                }
                            })
                    binding.accountsListRecyclerView.layoutManager =
                        LinearLayoutManager(requireContext())
                    binding.accountsListRecyclerView.setHasFixedSize(true)
                    binding.accountsListRecyclerView.visibility = View.VISIBLE
                    binding.loadingView.root.visibility = View.GONE
                }
            }
        }
    }
}