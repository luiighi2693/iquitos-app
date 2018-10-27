import { element, by, ElementFinder } from 'protractor';

export class ProductComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-product div table .btn-danger'));
    title = element.all(by.css('jhi-product div h2#page-heading span')).first();

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

export class ProductUpdatePage {
    pageTitle = element(by.id('jhi-product-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    codeInput = element(by.id('field_code'));
    descriptionInput = element(by.id('field_description'));
    dniInput = element(by.id('field_dni'));
    expirationDateInput = element(by.id('field_expirationDate'));
    isFavoriteInput = element(by.id('field_isFavorite'));
    visibleToSellInput = element(by.id('field_visibleToSell'));
    imageInput = element(by.id('file_image'));
    stockInput = element(by.id('field_stock'));
    stockLimitNotificationInput = element(by.id('field_stockLimitNotification'));
    productTypeSelect = element(by.id('field_productType'));
    unitMeasurementSelect = element(by.id('field_unitMeasurement'));
    categorySelect = element(by.id('field_category'));
    variantsSelect = element(by.id('field_variants'));
    sellHasProductSelect = element(by.id('field_sellHasProduct'));
    purchaseHasProductSelect = element(by.id('field_purchaseHasProduct'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setCodeInput(code) {
        await this.codeInput.sendKeys(code);
    }

    async getCodeInput() {
        return this.codeInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setDniInput(dni) {
        await this.dniInput.sendKeys(dni);
    }

    async getDniInput() {
        return this.dniInput.getAttribute('value');
    }

    async setExpirationDateInput(expirationDate) {
        await this.expirationDateInput.sendKeys(expirationDate);
    }

    async getExpirationDateInput() {
        return this.expirationDateInput.getAttribute('value');
    }

    getIsFavoriteInput() {
        return this.isFavoriteInput;
    }
    getVisibleToSellInput() {
        return this.visibleToSellInput;
    }
    async setImageInput(image) {
        await this.imageInput.sendKeys(image);
    }

    async getImageInput() {
        return this.imageInput.getAttribute('value');
    }

    async setStockInput(stock) {
        await this.stockInput.sendKeys(stock);
    }

    async getStockInput() {
        return this.stockInput.getAttribute('value');
    }

    async setStockLimitNotificationInput(stockLimitNotification) {
        await this.stockLimitNotificationInput.sendKeys(stockLimitNotification);
    }

    async getStockLimitNotificationInput() {
        return this.stockLimitNotificationInput.getAttribute('value');
    }

    async setProductTypeSelect(productType) {
        await this.productTypeSelect.sendKeys(productType);
    }

    async getProductTypeSelect() {
        return this.productTypeSelect.element(by.css('option:checked')).getText();
    }

    async productTypeSelectLastOption() {
        await this.productTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async unitMeasurementSelectLastOption() {
        await this.unitMeasurementSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async unitMeasurementSelectOption(option) {
        await this.unitMeasurementSelect.sendKeys(option);
    }

    getUnitMeasurementSelect(): ElementFinder {
        return this.unitMeasurementSelect;
    }

    async getUnitMeasurementSelectedOption() {
        return this.unitMeasurementSelect.element(by.css('option:checked')).getText();
    }

    async categorySelectLastOption() {
        await this.categorySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async categorySelectOption(option) {
        await this.categorySelect.sendKeys(option);
    }

    getCategorySelect(): ElementFinder {
        return this.categorySelect;
    }

    async getCategorySelectedOption() {
        return this.categorySelect.element(by.css('option:checked')).getText();
    }

    async variantsSelectLastOption() {
        await this.variantsSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async variantsSelectOption(option) {
        await this.variantsSelect.sendKeys(option);
    }

    getVariantsSelect(): ElementFinder {
        return this.variantsSelect;
    }

    async getVariantsSelectedOption() {
        return this.variantsSelect.element(by.css('option:checked')).getText();
    }

    async sellHasProductSelectLastOption() {
        await this.sellHasProductSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async sellHasProductSelectOption(option) {
        await this.sellHasProductSelect.sendKeys(option);
    }

    getSellHasProductSelect(): ElementFinder {
        return this.sellHasProductSelect;
    }

    async getSellHasProductSelectedOption() {
        return this.sellHasProductSelect.element(by.css('option:checked')).getText();
    }

    async purchaseHasProductSelectLastOption() {
        await this.purchaseHasProductSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async purchaseHasProductSelectOption(option) {
        await this.purchaseHasProductSelect.sendKeys(option);
    }

    getPurchaseHasProductSelect(): ElementFinder {
        return this.purchaseHasProductSelect;
    }

    async getPurchaseHasProductSelectedOption() {
        return this.purchaseHasProductSelect.element(by.css('option:checked')).getText();
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

export class ProductDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-product-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-product'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
