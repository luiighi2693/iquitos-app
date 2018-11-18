/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TipoDePagoComponentsPage, TipoDePagoDeleteDialog, TipoDePagoUpdatePage } from './tipo-de-pago.page-object';

const expect = chai.expect;

describe('TipoDePago e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let tipoDePagoUpdatePage: TipoDePagoUpdatePage;
    let tipoDePagoComponentsPage: TipoDePagoComponentsPage;
    let tipoDePagoDeleteDialog: TipoDePagoDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load TipoDePagos', async () => {
        await navBarPage.goToEntity('tipo-de-pago');
        tipoDePagoComponentsPage = new TipoDePagoComponentsPage();
        expect(await tipoDePagoComponentsPage.getTitle()).to.eq('Tipo De Pagos');
    });

    it('should load create TipoDePago page', async () => {
        await tipoDePagoComponentsPage.clickOnCreateButton();
        tipoDePagoUpdatePage = new TipoDePagoUpdatePage();
        expect(await tipoDePagoUpdatePage.getPageTitle()).to.eq('Create or edit a Tipo De Pago');
        await tipoDePagoUpdatePage.cancel();
    });

    it('should create and save TipoDePagos', async () => {
        const nbButtonsBeforeCreate = await tipoDePagoComponentsPage.countDeleteButtons();

        await tipoDePagoComponentsPage.clickOnCreateButton();
        await promise.all([tipoDePagoUpdatePage.setNombreInput('nombre')]);
        expect(await tipoDePagoUpdatePage.getNombreInput()).to.eq('nombre');
        await tipoDePagoUpdatePage.save();
        expect(await tipoDePagoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await tipoDePagoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last TipoDePago', async () => {
        const nbButtonsBeforeDelete = await tipoDePagoComponentsPage.countDeleteButtons();
        await tipoDePagoComponentsPage.clickOnLastDeleteButton();

        tipoDePagoDeleteDialog = new TipoDePagoDeleteDialog();
        expect(await tipoDePagoDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Tipo De Pago?');
        await tipoDePagoDeleteDialog.clickOnConfirmButton();

        expect(await tipoDePagoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
