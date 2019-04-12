package com.monzo.androidtest.common.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelGroup
import com.monzo.androidtest.R

class CardGroup(
    children: Collection<EpoxyModel<*>>
) : EpoxyModelGroup(R.layout.view_group_card, children) {
    constructor(vararg children: EpoxyModel<*>) : this(children.asList())
}