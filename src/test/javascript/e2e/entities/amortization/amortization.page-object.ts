import { element, by, ElementFinder } from 'protractor';

export class AmortizationComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-amortization div table .btn-danger'));
    title = element.all(by.css('jhi-amortization div h2#page-heading span')).first();

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

export class AmortizationUpdatePage {
    pageTitle = element(by.id('jhi-amortization-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    amountToPayInput = element(by.id('field_amountToPay'));
    amountPayedInput = element(by.id('field_amountPayed'));
    emissionDateInput = element(by.id('field_emissionDate'));
    documentCodeInput = element(by.id('field_documentCode'));
    glossInput = element(by.id('field_gloss'));
    documentTypeSellSelect = element(by.id('field_documentTypeSell'));
    paymentTypeSelect = element(by.id('field_paymentType'));
    sellSelect = element(by.id('field_sell'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setAmountToPayInput(amountToPay) {
        await this.amountToPayInput.sendKeys(amountToPay);
    }

    async getAmountToPayInput() {
        return this.amountToPayInput.getAttribute('value');
    }

    async setAmountPayedInput(amountPayed) {
        await this.amountPayedInput.sendKeys(amountPayed);
    }

    async getAmountPayedInput() {
        return this.amountPayedInput.getAttribute('value');
    }

    async setEmissionDateInput(emissionDate) {
        await this.emissionDateInput.sendKeys(emissionDate);
    }

    async getEmissionDateInput() {
        return this.emissionDateInput.getAttribute('value');
    }

    async setDocumentCodeInput(documentCode) {
        await this.documentCodeInput.sendKeys(documentCode);
    }

    async getDocumentCodeInput() {
        return this.documentCodeInput.getAttribute('value');
    }

    async setGlossInput(gloss) {
        await this.glossInput.sendKeys(gloss);
    }

    async getGlossInput() {
        return this.glossInput.getAttribute('value');
    }

    async documentTypeSellSelectLastOption() {
        await this.documentTypeSellSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async documentTypeSellSelectOption(option) {
        await this.documentTypeSellSelect.sendKeys(option);
    }

    getDocumentTypeSellSelect(): ElementFinder {
        return this.documentTypeSellSelect;
    }

    async getDocumentTypeSellSelectedOption() {
        return this.documentTypeSellSelect.element(by.css('option:checked')).getText();
    }

    async paymentTypeSelectLastOption() {
        await this.paymentTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async paymentTypeSelectOption(option) {
        await this.paymentTypeSelect.sendKeys(option);
    }

    getPaymentTypeSelect(): ElementFinder {
        return this.paymentTypeSelect;
    }

    async getPaymentTypeSelectedOption() {
        return this.paymentTypeSelect.element(by.css('option:checked')).getText();
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

export class AmortizationDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-amortization-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-amortization'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
