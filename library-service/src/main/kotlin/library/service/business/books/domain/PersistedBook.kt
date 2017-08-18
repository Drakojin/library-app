package library.service.business.books.domain

import library.service.business.books.domain.types.BorrowedState
import library.service.business.books.domain.types.Borrower
import library.service.business.books.exceptions.BookAlreadyBorrowedException
import library.service.business.books.exceptions.BookAlreadyReturnedException
import java.time.OffsetDateTime
import java.util.*

data class PersistedBook(
        val id: UUID,
        val book: Book,
        private var _borrowed: BorrowedState? = null
) {

    val borrowed: BorrowedState?
        get() = _borrowed

    fun borrow(by: Borrower, on: OffsetDateTime) {
        if (_borrowed != null) {
            throw BookAlreadyBorrowedException(id)
        }
        _borrowed = BorrowedState(by, on)
    }

    fun `return`() {
        if (_borrowed == null) {
            throw BookAlreadyReturnedException(id)
        }
        _borrowed = null
    }

}