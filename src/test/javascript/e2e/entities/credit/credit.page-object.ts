import { element, by, ElementFinder } from 'protractor';

export class CreditComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-credit div table .btn-danger'));
    title = element.all(by.css('jhi-credit div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class CreditUpdatePage {
    pageTitle = element(by.id('jhi-credit-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    amountInput = element(by.id('field_amount'));
    emissionDateInput = element(by.id('field_emissionDate'));
    paymentModeInput = element(by.id('field_paymentMode'));
    creditNumberInput = element(by.id('field_creditNumber'));
    totalAmountInput = element(by.id('field_totalAmount'));
    limitDateInput = element(by.id('field_limitDate'));
    creditNoteInput = element(by.id('field_creditNote'));
    sellSelect = element(by.id('field_sell'));
    purchaseSelect = element(by.id('field_purchase'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setAmountInput(amount) {
        await this.amountInput.sendKeys(amount);
    }

    async getAmountInput() {
        return this.amountInput.getAttribute('value');
    }

    async setEmissionDateInput(emissionDate) {
        await this.emissionDateInput.sendKeys(emissionDate);
    }

    async getEmissionDateInput() {
        return this.emissionDateInput.getAttribute('value');
    }

    async setPaymentModeInput(paymentMode) {
        await this.paymentModeInput.sendKeys(paymentMode);
    }

    async getPaymentModeInput() {
        return this.paymentModeInput.getAttribute('value');
    }

    async setCreditNumberInput(creditNumber) {
        await this.creditNumberInput.sendKeys(creditNumber);
    }

    async getCreditNumberInput() {
        return this.creditNumberInput.getAttribute('value');
    }

    async setTotalAmountInput(totalAmount) {
        await this.totalAmountInput.sendKeys(totalAmount);
    }

    async getTotalAmountInput() {
        return this.totalAmountInput.getAttribute('value');
    }

    async setLimitDateInput(limitDate) {
        await this.limitDateInput.sendKeys(limitDate);
    }

    async getLimitDateInput() {
        return this.limitDateInput.getAttribute('value');
    }

    async setCreditNoteInput(creditNote) {
        await this.creditNoteInput.sendKeys(creditNote);
    }

    async getCreditNoteInput() {
        return this.creditNoteInput.getAttribute('value');
    }

    async sellSelectLastOption() {
        await this.sellSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async sellSelectOption(option) {
        await this.sellSelect.sendKeys(option);
    }

    getSellSelect(): ElementFinder {
        return this.sellSelect;
    }

    async getSellSelectedOption() {
        return this.sellSelect.element(by.css('option:checked')).getText();
    }

    async purchaseSelectLastOption() {
        await this.purchaseSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async purchaseSelectOption(option) {
        await this.purchaseSelect.sendKeys(option);
    }

    getPurchaseSelect(): ElementFinder {
        return this.purchaseSelect;
    }

    async getPurchaseSelectedOption() {
        return this.purchaseSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class CreditDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-credit-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-credit'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
