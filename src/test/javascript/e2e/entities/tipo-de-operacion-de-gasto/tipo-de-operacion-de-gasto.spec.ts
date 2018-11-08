/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    TipoDeOperacionDeGastoComponentsPage,
    TipoDeOperacionDeGastoDeleteDialog,
    TipoDeOperacionDeGastoUpdatePage
} from './tipo-de-operacion-de-gasto.page-object';

const expect = chai.expect;

describe('TipoDeOperacionDeGasto e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let tipoDeOperacionDeGastoUpdatePage: TipoDeOperacionDeGastoUpdatePage;
    let tipoDeOperacionDeGastoComponentsPage: TipoDeOperacionDeGastoComponentsPage;
    let tipoDeOperacionDeGastoDeleteDialog: TipoDeOperacionDeGastoDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load TipoDeOperacionDeGastos', async () => {
        await navBarPage.goToEntity('tipo-de-operacion-de-gasto');
        tipoDeOperacionDeGastoComponentsPage = new TipoDeOperacionDeGastoComponentsPage();
        expect(await tipoDeOperacionDeGastoComponentsPage.getTitle()).to.eq('Tipo De Operacion De Gastos');
    });

    it('should load create TipoDeOperacionDeGasto page', async () => {
        await tipoDeOperacionDeGastoComponentsPage.clickOnCreateButton();
        tipoDeOperacionDeGastoUpdatePage = new TipoDeOperacionDeGastoUpdatePage();
        expect(await tipoDeOperacionDeGastoUpdatePage.getPageTitle()).to.eq('Create or edit a Tipo De Operacion De Gasto');
        await tipoDeOperacionDeGastoUpdatePage.cancel();
    });

    it('should create and save TipoDeOperacionDeGastos', async () => {
        const nbButtonsBeforeCreate = await tipoDeOperacionDeGastoComponentsPage.countDeleteButtons();

        await tipoDeOperacionDeGastoComponentsPage.clickOnCreateButton();
        await promise.all([tipoDeOperacionDeGastoUpdatePage.setNombreInput('nombre')]);
        expect(await tipoDeOperacionDeGastoUpdatePage.getNombreInput()).to.eq('nombre');
        await tipoDeOperacionDeGastoUpdatePage.save();
        expect(await tipoDeOperacionDeGastoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await tipoDeOperacionDeGastoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last TipoDeOperacionDeGasto', async () => {
        const nbButtonsBeforeDelete = await tipoDeOperacionDeGastoComponentsPage.countDeleteButtons();
        await tipoDeOperacionDeGastoComponentsPage.clickOnLastDeleteButton();

        tipoDeOperacionDeGastoDeleteDialog = new TipoDeOperacionDeGastoDeleteDialog();
        expect(await tipoDeOperacionDeGastoDeleteDialog.getDialogTitle()).to.eq(
            'Are you sure you want to delete this Tipo De Operacion De Gasto?'
        );
        await tipoDeOperacionDeGastoDeleteDialog.clickOnConfirmButton();

        expect(await tipoDeOperacionDeGastoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
